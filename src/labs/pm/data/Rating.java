/*
 * Copyright (c) 2021. Oracle Java SE 11 Programmer
 */

package labs.pm.data;

/**
 * {@code Rating} is an {@link java.lang.Enum Enum} instance represents
 * rating using five-point scale.
 *
 * @author Blazej Bazan
 * @version 5.0
 */
public enum Rating {
    NOT_RATED("\u2606\u2606\u2606\u2606\u2606"),
    ONE_STAR("\u2605\u2606\u2606\u2606\u2606"),
    TWO_STAR("\u2605\u2605\u2606\u2606\u2606"),
    THREE_STAR("\u2605\u2605\u2605\u2606\u2606"),
    FOUR_STAR("\u2605\u2605\u2605\u2605\u2606"),
    FIVE_STAR("\u2605\u2605\u2605\u2605\u2605");
    private String stars;

    Rating(String stars) {
        this.stars = stars;
    }

    public String getStars() {
        return stars;
    }
}
