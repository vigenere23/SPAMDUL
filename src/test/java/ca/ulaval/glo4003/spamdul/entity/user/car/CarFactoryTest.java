package ca.ulaval.glo4003.spamdul.entity.user.car;

import static com.google.common.truth.Truth.assertThat;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class CarFactoryTest {

  private final CarType A_CAR_TYPE = CarType.ECONOMIQUE;
  private final String A_BRAND = "brand";
  private final String A_MODEL = "model";
  private final int A_CAR_YEAR = 2020;
  private final String A_LICENSE_PLATE_STRING = "xxxxxx";
  private final LicensePlate A_LICENCE_PLATE = new LicensePlate(A_LICENSE_PLATE_STRING);

  private CarFactory carFactory;

  @Before
  public void setUp() throws Exception {
    carFactory = new CarFactory();
  }

  @Test
  public void whenCreatingNewCar_shouldCreateCarWithRightInfos() {
    Car car = carFactory.create(A_CAR_TYPE, A_BRAND, A_MODEL, A_CAR_YEAR, A_LICENSE_PLATE_STRING);

    assertThat(car.getCarId()).isNotNull();
    assertThat(car.getCarType()).isEqualTo(A_CAR_TYPE);
    assertThat(car.getBrand()).isEqualTo(A_BRAND);
    assertThat(car.getModel()).isEqualTo(A_MODEL);
    assertThat(car.getYear()).isEqualTo(A_CAR_YEAR);
    assertThat(car.getLicensePlate()).isEqualTo(A_LICENCE_PLATE);
  }

  @Test(expected = InvalidCarYearException.class)
  public void givenACarYearOverCurrentYear_whenCreatingCar_shouldThrowInvalidCarYearException() {
    int invalidYear = LocalDate.now().getYear() + 1;

    carFactory.create(A_CAR_TYPE, A_BRAND, A_MODEL, invalidYear, A_LICENSE_PLATE_STRING);
  }
}