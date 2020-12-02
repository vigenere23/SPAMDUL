package ca.ulaval.glo4003.spamdul.utils;

import static com.google.common.truth.Truth.assertThat;

import java.util.Map;
import org.junit.Test;

public class MapUtilTest {

  private static final String A_KEY = "somewf";
  private static final Integer A_VALUE = 129438;

  @Test
  public void givenKeyAndValue_whenConvertingToMap_shouldReturnRightMap() {
    Map<String, Integer> map = MapUtil.toMap(A_KEY, A_VALUE);
    assertThat(map.get(A_KEY)).isEqualTo(A_VALUE);
  }
}
