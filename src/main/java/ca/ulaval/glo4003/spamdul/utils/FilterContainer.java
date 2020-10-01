package ca.ulaval.glo4003.spamdul.utils;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.FilterDataNotSetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterContainer<T> {

  private List<T> originalData;
  private final List<Predicate<? super T>> filters = new ArrayList<>();

  public FilterContainer() {
  }

  public FilterContainer(List<T> dataList) {
    setData(dataList);
  }

  public void setData(List<T> dataList) {
    originalData = dataList;
  }

  public void addFilter(Predicate<? super T> filter) {
    filters.add(filter);
  }

  public List<T> getResults() {
    if (originalData == null) {
      throw new FilterDataNotSetException();
    }

    Stream<T> filterStream = originalData.stream();
    for (Predicate<? super T> filter : filters) {
      filterStream = filterStream.filter(filter);
    }

    return filterStream.collect(Collectors.toList());
  }
}
