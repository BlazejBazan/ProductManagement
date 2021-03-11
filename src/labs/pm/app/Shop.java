/*
 * Copyright (c) 2021. Oracle Java SE 11 Programmer
 */

package labs.pm.app;

import labs.pm.data.Drink;
import labs.pm.data.Food;
import labs.pm.data.Product;
import labs.pm.data.Rating;

import java.math.BigDecimal;
import java.time.LocalDate;

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
        Product p2 = new Drink(2, "Coffee", BigDecimal.valueOf(11.99), Rating.FIVE_STAR);
        Product p3 = new Food(3, "Cookie", BigDecimal.valueOf(3.99), Rating.FOUR_STAR, LocalDate.now().plusDays(2));
        Product p4 = new Product();
        Product p5 = new Drink(5, "Chocolate", BigDecimal.valueOf(3.99), Rating.FIVE_STAR);
        Product p6 = new Food(5, "Chocolate", BigDecimal.valueOf(3.99), Rating.FIVE_STAR, LocalDate.now().plusDays(5));

//        System.out.println(p1);
//        System.out.println(p2.toString());
//        System.out.println(p3);
//        System.out.println(p4);
//        p4 = p4.ApplyRating(Rating.TWO_STAR);
//        System.out.println(p4);
//        System.out.println(p5);
//        System.out.println(p6);

        System.out.println(p5.equals(p6));
    }
}
