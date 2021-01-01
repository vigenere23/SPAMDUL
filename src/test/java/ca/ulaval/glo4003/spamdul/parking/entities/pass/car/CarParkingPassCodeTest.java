package ca.ulaval.glo4003.spamdul.parking.entities.pass.car;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class CarParkingPassCodeTest {

  @Test
  public void givenValue_whenCreatingPassCode_shouldCreateWithValue() {
    String value = "123";
    CarParkingPassCode carParkingPassCode = CarParkingPassCode.valueOf(value);
    assertThat(carParkingPassCode.toString()).isEqualTo(value);
  }

  @Test
  public void whenComparingDifferentPassCode_shouldNotBeEqual() {
    CarParkingPassCode carParkingPassCode = CarParkingPassCode.valueOf("123");
    CarParkingPassCode anotherCarParkingPassCode = CarParkingPassCode.valueOf("456");

    assertThat(carParkingPassCode).isNotEqualTo(anotherCarParkingPassCode);
    assertThat(carParkingPassCode.hashCode()).isNotEqualTo(anotherCarParkingPassCode.hashCode());
  }

  @Test
  public void whenComparingTheSamePassCode_shouldBeEqual() {
    CarParkingPassCode carParkingPassCode = CarParkingPassCode.valueOf("123");
    CarParkingPassCode sameCarParkingPassCode = CarParkingPassCode.valueOf(carParkingPassCode.toString());

    assertThat(carParkingPassCode).isEqualTo(sameCarParkingPassCode);
    assertThat(carParkingPassCode.hashCode()).isEqualTo(sameCarParkingPassCode.hashCode());
  }
}
