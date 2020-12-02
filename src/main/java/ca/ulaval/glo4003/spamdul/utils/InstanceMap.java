package ca.ulaval.glo4003.spamdul.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InstanceMap {

  private final Map<Class, Object> instances = new HashMap<>();

  public <T> void add(Class<? super T> instanceClass, T instance) {
    ensureUniqueKey(instanceClass);
    instances.put(instanceClass, instance);
  }

  public <T> void add(T instance) {
    ensureUniqueKey(instance.getClass());
    instances.put(instance.getClass(), instance);
  }

  public <T> T get(Class<T> instanceClass) {
    return instanceClass.cast(instances.get(instanceClass));
  }

  public <T> boolean contains(Class<T> instanceClass) {
    return instances.get(instanceClass) != null;
  }

  public Set<Object> getValues() {
    return new HashSet<>(instances.values());
  }

  private <T> void ensureUniqueKey(Class<T> key) {
    if (contains(key)) {
      throw new RuntimeException(String.format("An instance is already present for class %s", key.getName()));
    }
  }
}
