package ca.ulaval.glo4003.spamdul.usecases.campusaccess.car;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.CarFactory;
import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.car.CarRepository;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import org.junit.Before;
import org.junit.Test;

public class CarServiceTest {

  private final String A_CAR_BRAND = "brand";
  private final String A_CAR_MODEL = "model";
  private final String A_LICENSE_PLATE = "license plate";
  private final CarId A_CAR_ID= CarId.valueOf("1");
  private final CarType A_CAR_TYPE = CarType.ECONOMIQUE;
  private final int A_YEAR = 2020;

  private CarRepository carRepository;
  private CarFactory carFactory;
  private CarDto carDto;
  private Car car;
  private CarService carService;

  @Before
  public void setUp() throws Exception {
    carDto = new CarDto();
    car = new Car(A_CAR_ID, A_CAR_TYPE, A_CAR_BRAND, A_CAR_MODEL, A_YEAR, A_LICENSE_PLATE);
    carRepository = mock(CarRepository.class);
    carFactory = mock(CarFactory.class);
    carService = new CarService(carFactory, carRepository);
  }

  @Test
  public void whenCreatingCar_shouldCallFactory() {
    carService.createCar(carDto);

    verify(carFactory, times(1)).create(carDto.carType, carDto.brand, carDto.model, carDto.year, carDto.licensePlate);
  }

  @Test
  public void whenCreatingCar_shouldSaveCarInRepo() {
    given(carFactory.create(carDto.carType, carDto.brand, carDto.model, carDto.year, carDto.licensePlate)).willReturn(
        car);

    carService.createCar(carDto);

    verify(carRepository, times(1)).save(this.car);
  }
}