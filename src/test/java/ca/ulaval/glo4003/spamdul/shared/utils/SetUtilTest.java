package ca.ulaval.glo4003.spamdul.shared.utils;

import static com.google.common.truth.Truth.assertThat;

import java.util.Set;
import org.junit.Test;

public class SetUtilTest {

  private static final String A_VALUE = "helldo";
  private static final String ANOTHER_VALUE = "iuhisd";

  @Test
  public void givenSingleValue_whenCreatingSet_shouldContainExactlyThatValue() {
    Set<String> set = SetUtil.toSet(A_VALUE);
    assertThat(set).containsExactly(A_VALUE);
  }

  @Test
  public void givenSameValues_whenCreatingSet_shouldContainExactlyThatValue() {
    Set<String> set = SetUtil.toSet(A_VALUE, A_VALUE);
    assertThat(set).containsExactly(A_VALUE);
  }

  @Test
  public void givenMultipleValues_whenCreatingSet_shouldContainExactlyThoseValues() {
    Set<String> set = SetUtil.toSet(A_VALUE, ANOTHER_VALUE);
    assertThat(set).containsExactly(A_VALUE, ANOTHER_VALUE);
  }
}
