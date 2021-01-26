package ca.ulaval.glo4003.spamdul.shared.infrastructure.filter;

import java.util.function.Function;
import java.util.function.Predicate;

public class MappedCondition<T, R> implements Predicate<T> {

    private final Function<T, R> mapper;
    private final Predicate<R> predicate;

    public MappedCondition(Function<T, R> mapper, Predicate<R> predicate) {
        this.mapper = mapper;
        this.predicate = predicate;
    }

    @Override
    public boolean test(T item) {
        return predicate.test(mapper.apply(item));
    }
}
