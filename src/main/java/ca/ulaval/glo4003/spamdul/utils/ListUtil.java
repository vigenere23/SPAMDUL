package ca.ulaval.glo4003.spamdul.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListUtil {

  @SafeVarargs
  public static <T> List<T> toList(T... items) {
    List<T> list = new ArrayList<>();
    Collections.addAll(list, items);

    return list;
  }
}
