/*
 * Copyright (c) 2021. Oracle Java SE 11 Programmer
 */

package labs.pm.data;

@FunctionalInterface
public interface Rateable<T> {
    public static final Rating DEFAULT_RATE = Rating.NOT_RATED;

    public static Rating convert(int stars) {
        return (stars >= 0 && stars <= 5) ? Rating.values()[stars] : DEFAULT_RATE;
    }

    T applyRating(Rating rating);

    public default T applyRating(int stars) {
        return applyRating(convert(stars));
    }

    public default Rating getRating() {
        return DEFAULT_RATE;
    }
}
