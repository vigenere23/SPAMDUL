package ca.ulaval.glo4003.spamdul.entity.car;

import static com.google.common.truth.Truth.assertThat;

import java.time.LocalDate;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class CarFactoryTest {

  private final CarType A_CAR_TYPE = CarType.ECONOMIQUE;
  private final String A_BRAND = "brand";
  private final String A_MODEL = "model";
  private final int A_CAR_YEAR = 2020;
  private final String A_LICENCE_PLATE = "xxxxxx";

  private CarFactory carFactory;

  @Before
  public void setUp() throws Exception {
    carFactory = new CarFactory();
  }

  @Test
  public void whenCreatingNewCar_shouldCreateCarWithRightInfos() {
    Car car = carFactory.create(A_CAR_TYPE, A_BRAND, A_MODEL, A_CAR_YEAR, A_LICENCE_PLATE);

    assertThat(car.getCarId()).isNotNull();
    assertThat(car.getCarType()).isEqualTo(A_CAR_TYPE);
    assertThat(car.getBrand()).isEqualTo(A_BRAND);
    assertThat(car.getModel()).isEqualTo(A_MODEL);
    assertThat(car.getYear()).isEqualTo(A_CAR_YEAR);
    assertThat(car.getLicencePlate()).isEqualTo(A_LICENCE_PLATE);
  }

  @Test(expected = InvalidCarYearException.class)
  public void givenACarYearOverCurrentYear_whenCreatingCar_shouldThrowInvalidCarYearException() {
    carFactory.create(A_CAR_TYPE, A_BRAND, A_MODEL, LocalDate.now().getYear() + 1, A_LICENCE_PLATE);
  }
}