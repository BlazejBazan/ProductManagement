/*
 * Copyright (c) 2021. Oracle Java SE 11 Programmer
 */

package labs.pm.app;

import labs.pm.data.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Predicate;

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
        ProductManager pm = new ProductManager("en-GB");

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
