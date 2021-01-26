package ca.ulaval.glo4003.spamdul.shared.utils;

public class Comparator<T> {

    private final Comparable<T> item;

    public Comparator(Comparable<T> item) {
        this.item = item;
    }

    public boolean isBefore(T other) {
        return item.compareTo(other) < 0;
    }

    public boolean isAfter(T other) {
        return item.compareTo(other) > 0;
    }

    public boolean isEqualTo(T other) {
        return item.compareTo(other) == 0;
    }

    public boolean isBetween(T min, T max) {
        return !isBefore(min) && !isAfter(max);
    }
}
