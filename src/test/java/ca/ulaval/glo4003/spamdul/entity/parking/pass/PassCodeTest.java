package ca.ulaval.glo4003.spamdul.entity.parking.pass;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class PassCodeTest {

  @Test
  public void whenCreatingPassCode_shouldGenerateACode() {
    PassCode passCode = new PassCode();

    assertThat(passCode.toString()).isNotEqualTo("");
  }

  @Test
  public void whenComparingDifferentPassCode_shouldNotBeEqual() {
    PassCode passCode = new PassCode();
    PassCode anotherPassCode = new PassCode();

    assertThat(passCode).isNotEqualTo(anotherPassCode);
    assertThat(passCode.hashCode()).isNotEqualTo(anotherPassCode.hashCode());
  }

  @Test
  public void whenComparingTheSamePassCode_shouldBeEqual() {
    PassCode passCode = new PassCode();
    PassCode samePassCode = PassCode.valueOf(passCode.toString());

    assertThat(passCode).isEqualTo(samePassCode);
    assertThat(passCode.hashCode()).isEqualTo(samePassCode.hashCode());
  }
}