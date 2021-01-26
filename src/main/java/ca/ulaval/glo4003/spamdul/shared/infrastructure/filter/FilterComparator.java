package ca.ulaval.glo4003.spamdul.shared.infrastructure.filter;

import ca.ulaval.glo4003.spamdul.shared.utils.Comparator;

import java.util.function.Function;

public class FilterComparator<T, R, S> {

    private final Filter<T> filter;
    private final Function<T, Comparable<? super R>> mapper;
    private final S returnedObject;

    public FilterComparator(Filter<T> filter, Function<T, Comparable<? super R>> mapper, S returnedObject) {
        this.filter = filter;
        this.mapper = mapper;
        this.returnedObject = returnedObject;
    }

    public S before(R other) {
        filter.addCondition(new MappedCondition<>(mapper, item -> new Comparator<>(item).isBefore(other)));
        return returnedObject;
    }

    public S after(R other) {
        filter.addCondition(new MappedCondition<>(mapper, item -> new Comparator<>(item).isAfter(other)));
        return returnedObject;
    }

    public S between(R min, R max) {
        if (min == null) {
            return before(max);
        } else if (max == null) {
            return after(min);
        } else {
            filter.addCondition(new MappedCondition<>(mapper, item -> new Comparator<>(item).isBetween(min ,max)));
        }
        return returnedObject;
    }
}
