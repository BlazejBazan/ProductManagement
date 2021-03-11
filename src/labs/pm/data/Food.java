/*
 * Copyright (c) 2021. Oracle Java SE 11 Programmer
 */

package labs.pm.data;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Food extends Product {
    private LocalDate bestBefore;

    public Food(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore) {
        super(id, name, price, rating);
        this.bestBefore = bestBefore;
    }

    @Override
    public String toString() {
        return super.toString().substring(0, super.toString().length() - 1) +
                ", bestBefore=" + bestBefore +
                "}";
    }

    /**
     * Get the value of the best before date for the product.
     *
     * @return the value of bestBefore
     */
    public LocalDate getBestBefore() {
        return bestBefore;
    }
}
