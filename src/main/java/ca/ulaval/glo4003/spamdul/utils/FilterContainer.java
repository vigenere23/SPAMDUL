package ca.ulaval.glo4003.spamdul.utils;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterContainer<T> {
    private Stream<T> filterStream = Stream.empty();

    public FilterContainer() {}

    public FilterContainer(List<T> dataList) {
        setData(dataList);
    }

    public FilterContainer(Stream<T> dataStream) {
        setData(dataStream);
    }

    public void setData(List<T> dataList) {
        filterStream = dataList.stream();
    }

    public void setData(Stream<T> dataStream) {
        filterStream = dataStream;
    }

    public void addFilter(Predicate<? super T> predicate) {
        filterStream = filterStream.filter(predicate);
    }

    public List<T> getResults() {
        List<T> results = filterStream.collect(Collectors.toList());
        filterStream = Stream.empty();
        return results;
    }
}
