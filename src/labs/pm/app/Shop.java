/*
 * Copyright (c) 2021. Oracle Java SE 11 Programmer
 */

package labs.pm.app;

import labs.pm.data.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;

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
        ProductManager pm = new ProductManager(Locale.UK);
        Product p1 = pm.createProduct(1, "Tea", BigDecimal.valueOf(1.99), Rating.NOT_RATED);
        pm.printProductReport(p1);
        p1 = pm.reviewProduct(p1, Rating.FOUR_STAR, "Nice hot cup of tea!");
        p1 = pm.reviewProduct(p1, Rating.FIVE_STAR, "I love this tea!");
        p1 = pm.reviewProduct(p1, Rating.ONE_STAR, "Awful");
        p1 = pm.reviewProduct(p1, Rating.ONE_STAR, "Taste like socks");
        p1 = pm.reviewProduct(p1, Rating.FOUR_STAR, "Not bad");
        pm.printProductReport(p1);
//        Product p2 = pm.createProduct(2, "Coffee", BigDecimal.valueOf(11.99), Rating.FIVE_STAR);
//        Product p3 = pm.createProduct(3, "Cookie", BigDecimal.valueOf(2.99), Rating.FOUR_STAR, LocalDate.now());
//        Product p4 = pm.createProduct(4, "Cake", BigDecimal.valueOf(8.99), Rating.THREE_STAR, LocalDate.now().plusDays(3));
//        Product p5 = pm.createProduct(5, "Chocolate", BigDecimal.valueOf(3.99), Rating.TWO_STAR);
//        Product p6 = pm.createProduct(5, "Chocolate", BigDecimal.valueOf(3.99), Rating.FIVE_STAR, LocalDate.now().plusDays(5));
//
//        System.out.println(p1);
//        System.out.println(p2.toString());
//        System.out.println(p3);
//        System.out.println(p4);
//        p4 = p4.applyRating(Rating.TWO_STAR);
//        System.out.println(p4);
//        System.out.println(p5);
//        System.out.println(p6);

//        System.out.println(p5.equals(p6));


    }
}
