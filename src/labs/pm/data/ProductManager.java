/*
 * Copyright (c) 2021. Oracle Java SE 11 Programmer
 */

package labs.pm.data;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * {@code ProductManager} is a class with factory methods.
 *
 * @version 6.0
 */
public class ProductManager {
    private static final Logger LOGGER = Logger.getLogger(ProductManagerException.class.getName());
    private static final Map<String, ResourceFormatter> formatters =
            Map.of("en-GB", new ResourceFormatter(Locale.UK),
                    "en-US", new ResourceFormatter(Locale.US),
                    "fr-FR", new ResourceFormatter(Locale.FRANCE),
                    "ru-RU", new ResourceFormatter(new Locale("ru", "RU")),
                    "zh-CN", new ResourceFormatter(Locale.CHINA),
                    "pl-PL", new ResourceFormatter(new Locale("pl", "PL")));
    private final Map<Product, List<Review>> products = new HashMap<>();
    private ResourceFormatter formatter;
    private final ResourceBundle config = ResourceBundle.getBundle("labs.pm.data.config");
    private final MessageFormat productFormat = new MessageFormat(config.getString("product.data.format"));
    private final MessageFormat reviewFormat = new MessageFormat(config.getString("review.data.format"));
    private final Path reportsFolder = Path.of(config.getString("reports.folder"));
    private final Path tempFolder = Path.of(config.getString("temp.folder"));
    private final Path dataFolder = Path.of(config.getString("data.folder"));

    public ProductManager(Locale locale) {
        this(locale.toLanguageTag());
    }

    public ProductManager(String languageTag) {
        changeLocale(languageTag);
    }

    public static Set<String> getSupportedLocales() {
        return formatters.keySet();
    }

    public void changeLocale(String languageTag) {
        formatter = formatters.getOrDefault(languageTag, formatters.get("en-GB"));
    }

    public Product findProduct(int id) throws ProductManagerException {
        return products.keySet()
                .stream()
                .filter(product -> product.getId() == id).findFirst()
                .orElseThrow(() -> new ProductManagerException("Product " + id + " not found"));
    }

    public void printProductReport(int id) {
        try {
            printProductReport(findProduct(id));
        } catch (ProductManagerException e) {
            LOGGER.log(Level.INFO, e.getMessage());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error printing product report " + e.getMessage(), e);
        }
    }

    public void printProductReport(Product product) throws IOException {
        List<Review> reviews = products.get(product);
        Path productFile = reportsFolder.resolve(MessageFormat.format(
                config.getString("report.file"), product.getId()));

        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(Files.newOutputStream(
                productFile, StandardOpenOption.CREATE), StandardCharsets.UTF_8))) {
            out.append(formatter.formatProduct(product)).append(System.lineSeparator());
            Collections.sort(reviews);

            if (reviews.isEmpty()) {
                out.append(formatter.getText("no.reviews")).append(System.lineSeparator());
            } else {
                out.append(reviews.stream()
                        .map(review -> formatter.formatReview(review) + System.lineSeparator())
                        .collect(Collectors.joining())
                );
            }
        }
    }

    public void printProducts(Predicate<Product> filter, Comparator<Product> sorter) {
        StringBuilder txt = new StringBuilder();
        products.keySet()
                .stream()
                .sorted(sorter)
                .filter(filter)
                .forEach(product -> txt.append(formatter.formatProduct(product)).append('\n'));
        System.out.println(txt);
    }

    /**
     * @return new instance of {@link Food Food} class.
     */
    public Product createProduct(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore) {
        Product product = new Food(id, name, price, rating, bestBefore);
        products.putIfAbsent(product, new ArrayList<>());
        return product;
    }

    /**
     * @return new instance of {@link Drink Drink} class.
     */
    public Product createProduct(int id, String name, BigDecimal price, Rating rating) {
        Product product = new Drink(id, name, price, rating);
        products.putIfAbsent(product, new ArrayList<>());
        return product;
    }

    public Product reviewProduct(int id, Rating rating, String comments) {
        try {
            return reviewProduct(findProduct(id), rating, comments);
        } catch (ProductManagerException e) {
            LOGGER.log(Level.INFO, e.getMessage());
        }
        return null;
    }

    public Product reviewProduct(Product product, Rating rating, String comments) {
        List<Review> reviews = products.get(product);
        products.remove(product, reviews);
        reviews.add(new Review(rating, comments));

        product = product.applyRating(
                Rateable.convert(
                        (int) Math.round(
                                reviews.stream()
                                        .mapToInt(review -> review.getRating().ordinal())
                                        .average()
                                        .orElseGet(() -> 0)
                        )
                )
        );

        products.put(product, reviews);
        return product;
    }

    public Map<String, String> getDiscounts() {
        return products.keySet()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                product -> product.getRating().getStars(),
                                Collectors.collectingAndThen(
                                        Collectors.summingDouble(
                                                product -> product.getDiscount().doubleValue()),
                                        discount -> formatter.moneyFormat.format(discount))
                        )
                );
    }

    public void parseReview(String text) {
        try {
            Object[] values = reviewFormat.parse(text);
            reviewProduct(Integer.parseInt((String) values[0])
                    , Rateable.convert(Integer.parseInt((String) values[1]))
                    , (String) values[2]);
        } catch (ParseException | NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Error parsing review " + text);
        }
    }

    public void parseProduct(String text) {
        try {
            Object[] values = productFormat.parse(text);
            int id = Integer.parseInt((String) values[1]);
            String name = (String) values[2];
            BigDecimal price = BigDecimal.valueOf(Double.parseDouble((String) values[3]));
            Rating rating = Rateable.convert(Integer.parseInt((String) values[4]));
            switch ((String) values[0]) {
                case "D":
                    createProduct(id, name, price, rating);
                    break;
                case "F":
                    LocalDate bestBefore = LocalDate.parse((String) values[5]);
                    createProduct(id, name, price, rating, bestBefore);
                    break;
            }
        } catch (ParseException | NumberFormatException | DateTimeException e) {
            LOGGER.log(Level.WARNING, "Error parsing product " + text + " " + e.getMessage());
        }
    }

    private static class ResourceFormatter {
        private final Locale locale;
        private final ResourceBundle resources;
        private final DateTimeFormatter dateFormat;
        private final NumberFormat moneyFormat;

        private ResourceFormatter(Locale locale) {
            this.locale = locale;
            resources = ResourceBundle.getBundle("labs.pm.data.resource", locale);
            dateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).localizedBy(locale);
            moneyFormat = NumberFormat.getCurrencyInstance(locale);
        }

        private String formatProduct(Product product) {
            return MessageFormat.format(resources.getString("product"),
                    product.getName(),
                    moneyFormat.format(product.getPrice()),
                    product.getRating().getStars(),
                    dateFormat.format(product.getBestBefore()));
        }

        private String formatReview(Review review) {
            return MessageFormat.format(resources.getString("review"),
                    review.getRating().getStars(),
                    review.getComments());
        }

        private String getText(String key) {
            return resources.getString(key);
        }
    }


}
