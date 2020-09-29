package ca.ulaval.glo4003.spamdul.infrastructure.db.car;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import org.junit.Before;
import org.junit.Test;

public class InMemoryCarRepositoryTest {

  private final CarId A_CAR_ID = new CarId();
  private final Car A_CAR = new Car(A_CAR_ID, CarType.GOURMANDE, "brand", "model", 2020, "xxx xxx");

  private InMemoryCarRepository carRepository;

  @Before
  public void setUp() throws Exception {
    carRepository = new InMemoryCarRepository();
  }

  @Test
  public void whenSavingCar_shouldSaveCarInRepository() {
    carRepository.save(A_CAR);

    assertThat(carRepository.findById(A_CAR_ID)).isEqualTo(A_CAR);
  }
}