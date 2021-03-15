/*
 * Copyright (c) 2021. Oracle Java SE 11 Programmer
 */

package labs.pm.data;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class Food extends Product {
    private final LocalDate bestBefore;

    Food(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore) {
        super(id, name, price, rating);
        this.bestBefore = bestBefore;
    }

    @Override
    public BigDecimal getDiscount() {
        return bestBefore.isEqual(LocalDate.now()) ? super.getDiscount() : BigDecimal.ZERO;
    }

    @Override
    public Product applyRating(Rating newRating) {
        return new Food(getId(), getName(), getPrice(), newRating, getBestBefore());
    }

    @Override
    public Rating getRating() {
        return super.getRating();
    }

    /**
     * Get the value of the best before date for the product.
     *
     * @return the value of bestBefore
     */
    @Override
    public LocalDate getBestBefore() {
        return bestBefore;
    }
}
