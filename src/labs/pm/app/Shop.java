/*
 * Copyright (c) 2021. Oracle Java SE 11 Programmer
 */

package labs.pm.app;

import labs.pm.data.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@code Shop} class represents an application that manages Products
 *
 * @author Blazej Bazan
 * @version 4.0
 */

public class Shop {
    /**
     * The entry point of application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //TODO Application logic

        ProductManager pm = ProductManager.getInstance();
        AtomicInteger clientCount = new AtomicInteger(0);
        Callable<String> client = () -> {
            String clientId = "Client " + clientCount.incrementAndGet();
            String threadName = Thread.currentThread().getName();
            int productId = ThreadLocalRandom.current().nextInt(5) + 1;
            String languageTaag = ProductManager.getSupportedLocales()
                    .stream()
                    .skip(ThreadLocalRandom.current().nextInt(5))
                    .findFirst()
                    .get();
            StringBuilder log = new StringBuilder();
            log.append(clientId + " " + threadName + "\n-\tstart of log\t-\n");

            log.append(pm.getDiscounts(languageTaag)
                    .entrySet()
                    .stream()
                    .map(entry -> entry.getKey() + "\t" + entry.getValue())
                    .collect(Collectors.joining("\n")));
            Product product = pm.reviewProduct(productId, Rating.FOUR_STAR, "Yet another review");
            log.append((product != null) ? "\nProduct " + productId + " reviewed\n" : "\nProduct " + productId + " not reviewed\n");
            pm.printProductReport(productId, languageTaag, clientId);
            log.append(clientId + " generated report for " + productId + " product");

            log.append("\n-\tend of log\t-\n");
            return log.toString();
        };
        List<Callable<String>> clients = Stream.generate(() -> client).limit(5).collect(Collectors.toList());
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        try {
            List<Future<String>> results = executorService.invokeAll(clients);
            executorService.shutdown();
            results.forEach(result -> {
                try {
                    System.out.println(result.get());
                } catch (InterruptedException | ExecutionException e) {
                    Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, "Error retrieving client log", e);
                }
            });
        } catch (InterruptedException e) {
            Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, "Error invoking clients", e);
        }

/*
        pm.createProduct(1, "Tea", BigDecimal.valueOf(1.99), Rating.NOT_RATED);
        pm.reviewProduct(1, Rating.THREE_STAR, "Nice hot cup of tea");
        pm.reviewProduct(1, Rating.FIVE_STAR, "Hot tea");
        pm.reviewProduct(1, Rating.TWO_STAR, "Nice tea");
        pm.reviewProduct(1, Rating.TWO_STAR, "Nice");
        pm.reviewProduct(1, Rating.ONE_STAR, "Bad tea");
        pm.printProductReport(1);

        pm.createProduct(2, "Coffee", BigDecimal.valueOf(11.99), Rating.FIVE_STAR);
        pm.reviewProduct(2, Rating.FOUR_STAR, "Nice hot cup of tea!");
        pm.reviewProduct(2, Rating.FIVE_STAR, "I love this tea!");
        pm.reviewProduct(2, Rating.ONE_STAR, "Awful");
        pm.reviewProduct(2, Rating.ONE_STAR, "Taste like socks");
        pm.reviewProduct(2, Rating.FOUR_STAR, "Not bad");
        pm.printProductReport(2);

//        pm.dumpData();
//        pm.restoreData();

        pm.printProductReport(2);

        pm.printProducts(Objects::nonNull, Comparator.comparing(Product::getPrice));
*/

//        pm.createProduct(3, "Cookie", BigDecimal.valueOf(2.99), Rating.FOUR_STAR, LocalDate.now());
//        pm.createProduct(4, "Cake", BigDecimal.valueOf(8.99), Rating.THREE_STAR, LocalDate.now().plusDays(3));
//        pm.createProduct(5, "Chocolate", BigDecimal.valueOf(3.99), Rating.TWO_STAR);
//        pm.createProduct(6, "Chocolate", BigDecimal.valueOf(3.99), Rating.FIVE_STAR, LocalDate.now().plusDays(5));
//
//        pm.printProductReport(3);
//        pm.printProductReport(4);
//        pm.printProductReport(5);
//        pm.printProductReport(6);
//
//        Comparator<Product> priceSorter = Comparator.comparing(Product::getPrice);
//        Comparator<Product> priceSorter = (p1, p2) -> p1.getPrice().compareTo(p2.getPrice());
//        Comparator<Product> ratingSorter = (p1, p2) -> p2.getRating().ordinal() - p1.getRating().ordinal();
//
//        Predicate<Product> priceLessThanFiveFilter = product -> product.getPrice().floatValue() < 5;
//
//        pm.printProducts(priceLessThanFiveFilter, ratingSorter.thenComparing(priceSorter));
//        pm.printProducts(priceLessThanFiveFilter, priceSorter);
//
//        pm.getDiscounts().forEach((rating, discount) -> System.out.println(rating + '\t' + discount));
    }
}
