package ca.ulaval.glo4003.spamdul.shared.utils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SetUtil {

  @SafeVarargs public static <T> Set<T> toSet(T... items) {
    return Arrays.stream(items).collect(Collectors.toSet());
  }
}
