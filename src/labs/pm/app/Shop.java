/*
 * Copyright (c) 2021. Oracle Java SE 11 Programmer
 */

package labs.pm.app;

import labs.pm.data.Product;
import labs.pm.data.Rating;

import java.math.BigDecimal;

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
        Product p1 = new Product(1, "Tea", BigDecimal.valueOf(1.99));
        Product p2 = new Product(2, "Coffee", BigDecimal.valueOf(11.99), Rating.FIVE_STAR);
        Product p3 = new Product(3, "Cookie", BigDecimal.valueOf(3.99), Rating.FOUR_STAR);
        Product p4 = new Product();

        System.out.println(p1.toString());
        System.out.println(p2.toString());
        System.out.println(p3.toString());
        System.out.println(p4.toString());

        p4 = p4.ApplyRating(Rating.TWO_STAR);

        System.out.println(p4.toString());

    }
}
