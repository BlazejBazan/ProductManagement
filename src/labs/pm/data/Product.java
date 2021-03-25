/*
 * Copyright (c) 2021. Oracle Java SE 11 Programmer
 */

package labs.pm.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

import static labs.pm.data.Rating.*;

/**
 * {@code Product} class represents properties and behaviours of
 * product objects in the Product Management System.
 * <br>
 * Each product has an id, name, price
 * <br>
 * Each product can have a discount, calculated based on a
 * {@link DISCOUNT_RATE discount rate}
 *
 * @author Blazej Bazan
 * @version 4.0
 */

public abstract class Product implements Rateable<Product>, Serializable {

    /**
     * A constant that defines a {@link java.math.BigDecimal BigDecimal} value
     * of the discount rate
     * <br>
     * Discount rate is 10%
     */
    public static final BigDecimal DISCOUNT_RATE = BigDecimal.valueOf(0.1);
    private final int id;
    private final String name;
    private final BigDecimal price;
    private final Rating rating;

    //static {id = ++id;}

    Product(int id, String name, BigDecimal price, Rating rating) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.rating = rating;
    }

    Product(int id, String name, BigDecimal price) {
        this(id, name, price, NOT_RATED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "{id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", discount=" + getDiscount() +
                ", rating=" + rating.getStars() +
                ", bestBefore=" + getBestBefore() +
                "}";
    }

    /**
     * @return value of {@link Rating rating} for instance
     * of {@link Product product}.
     */
    @Override
    public Rating getRating() {
        return rating;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Get the value of the best before date for the product.
     *
     * @return the value of bestBefore
     */
    public LocalDate getBestBefore() {
        return LocalDate.now();
    }

    /**
     * Calculate discount based on a product price and
     * {@link DISCOUNT_RATE discount rate}.
     *
     * @return a {@link java.math.BigDecimal BigDecimal}
     * value of the discount
     */
    public BigDecimal getDiscount() {
        return price.multiply(DISCOUNT_RATE).setScale(2, RoundingMode.HALF_UP);
    }
}
