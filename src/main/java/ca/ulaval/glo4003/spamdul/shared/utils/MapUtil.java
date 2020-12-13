package ca.ulaval.glo4003.spamdul.shared.utils;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {

  public static <Key, Value> Map<Key, Value> toMap(Key key, Value value) {
    Map<Key, Value> map = new HashMap<>();
    map.put(key, value);

    return map;
  }
}
