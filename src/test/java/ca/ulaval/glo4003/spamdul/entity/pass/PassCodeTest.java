package ca.ulaval.glo4003.spamdul.entity.pass;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class PassCodeTest {

  @Test
  public void givenValue_whenCreatingPassCode_shouldCreateWithValue() {
    String value = "123";
    PassCode passCode = PassCode.valueOf(value);
    assertThat(passCode.toString()).isEqualTo(value);
  }

  @Test
  public void whenComparingDifferentPassCode_shouldNotBeEqual() {
    PassCode passCode = PassCode.valueOf("123");
    PassCode anotherPassCode = PassCode.valueOf("456");

    assertThat(passCode).isNotEqualTo(anotherPassCode);
    assertThat(passCode.hashCode()).isNotEqualTo(anotherPassCode.hashCode());
  }

  @Test
  public void whenComparingTheSamePassCode_shouldBeEqual() {
    PassCode passCode = PassCode.valueOf("123");
    PassCode samePassCode = PassCode.valueOf(passCode.toString());

    assertThat(passCode).isEqualTo(samePassCode);
    assertThat(passCode.hashCode()).isEqualTo(samePassCode.hashCode());
  }
}
