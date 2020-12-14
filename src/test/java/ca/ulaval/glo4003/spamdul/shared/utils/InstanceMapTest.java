package ca.ulaval.glo4003.spamdul.shared.utils;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Before;
import org.junit.Test;

public class InstanceMapTest {

  static class Parent {

  }

  static class Child extends Parent {

  }

  private InstanceMap instanceMap;

  @Before
  public void setUp() {
    instanceMap = new InstanceMap();
  }

  @Test
  public void givenInitialized_shouldBeEmpty() {
    assertThat(instanceMap.getValues()).isEmpty();
  }

  @Test
  public void whenAddingWithParentClass_shouldAddAsParentClass() {
    instanceMap.add(Parent.class, new Child());

    assertThat(instanceMap.contains(Parent.class)).isTrue();
  }

  @Test
  public void whenAddingWithoutParentClass_shouldAddWithInstanceClass() {
    instanceMap.add(new Child());

    assertThat(instanceMap.contains(Child.class)).isTrue();
  }

  @Test(expected = RuntimeException.class)
  public void whenAddingMultipleTimesWithSameParentClass_shouldThrowException() {
    instanceMap.add(new Child());
    instanceMap.add(new Child());
  }

  @Test
  public void whenAddingSameInstanceWithDifferentParentClasses_shouldReturnSameInstance() {
    Child instance = new Child();
    instanceMap.add(instance);
    instanceMap.add(Parent.class, instance);

    assertThat(instanceMap.get(Child.class)).isEqualTo(instanceMap.get(Parent.class));
  }
}
