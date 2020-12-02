package ca.ulaval.glo4003.spamdul.utils;

import static com.google.common.truth.Truth.assertThat;

import java.util.HashSet;
import java.util.Set;

public class Matchers {

  public static void assertContainsExactlyInstancesOf(Set<Object> objectsToCheck, Class... classes) {
    Set<Class> checkedClasses = new HashSet<>();
    Set<Object> checkedObjects = new HashSet<>();
    Set<Class> classesToCheck = SetUtil.toSet(classes);

    objectsToCheck.forEach(object -> {
      for (Class aClass : classesToCheck) {
        if (aClass.isInstance(object)) {
          checkedClasses.add(aClass);
          checkedObjects.add(object);
          return;
        }
      }
    });

    assertThat(checkedClasses).isEqualTo(classesToCheck);
    assertThat(checkedObjects).isEqualTo(objectsToCheck);
  }
}
