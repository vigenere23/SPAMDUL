package ca.ulaval.glo4003.spamdul.entity.car;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class CarTest {

  private final String A_CAR_BRAND = "brand";
  private final String A_CAR_MODEL = "model";
  private final CarId A_CAR_ID = CarId.valueOf("1");
  private final CarType A_CAR_TYPE = CarType.ECONOMIQUE;
  private final int A_YEAR = 2020;
  private String A_LICENSE_PLATE_STRING = "abc def";
  private LicensePlate A_LICENSE_PLATE = new LicensePlate(A_LICENSE_PLATE_STRING);
  private String ANOTHER_LICENSE_PLATE_STRING = "xyz efg";

  @Test
  public void givenTheSameLicensePlate_whenVerifyingLicensePlate_shouldReturnSameLicensePlate() {
    Car car = new Car(A_CAR_ID, A_CAR_TYPE, A_CAR_BRAND, A_CAR_MODEL, A_YEAR, A_LICENSE_PLATE);
    LicensePlate licensePlate = new LicensePlate(A_LICENSE_PLATE_STRING);

    boolean isSameLicensePlate = car.validateLicensePlate(licensePlate);

    assertThat(isSameLicensePlate).isTrue();
  }

  @Test
  public void givenAnotherLicensePlate_whenVerifyingLicensePlate_shouldReturnNotSameLicensePlate() {
    Car car = new Car(A_CAR_ID, A_CAR_TYPE, A_CAR_BRAND, A_CAR_MODEL, A_YEAR, A_LICENSE_PLATE);
    LicensePlate licensePlate = new LicensePlate(ANOTHER_LICENSE_PLATE_STRING);

    boolean isSameLicensePlate = car.validateLicensePlate(licensePlate);

    assertThat(isSameLicensePlate).isFalse();
  }
}