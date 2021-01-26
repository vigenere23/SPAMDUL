package ca.ulaval.glo4003.spamdul.shared.infrastructure.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Filter<T> {

  private List<T> originalData;
  private final List<Predicate<T>> conditions = new ArrayList<>();

  public void setData(List<T> dataList) {
    originalData = dataList;
  }

  public void addCondition(Predicate<T> condition) {
    conditions.add(condition);
  }

  public List<T> getResults() {
    if (originalData == null) {
      throw new FilterDataNotSetException();
    }

    Stream<T> filterStream = originalData.stream();
    for (Predicate<T> condition : conditions) {
      filterStream = filterStream.filter(condition);
    }

    return filterStream.collect(Collectors.toList());
  }
}
