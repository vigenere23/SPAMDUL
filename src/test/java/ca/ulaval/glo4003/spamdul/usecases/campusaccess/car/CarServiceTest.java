package ca.ulaval.glo4003.spamdul.usecases.campusaccess.car;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.CarFactory;
import ca.ulaval.glo4003.spamdul.entity.car.CarRepository;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import org.junit.Before;
import org.junit.Test;

public class CarServiceTest {

  private final String A_CAR_BRAND = "brand";
  private final String A_CAR_MODEL = "model";
  private final String A_LICENSE_PLATE = "license plate";
  private final UserId A_USER_ID = UserId.valueOf("1");
  private final int A_YEAR = 2020;

  private CarRepository carRepository;
  private CarFactory carFactory;
  private CarDto carDto;
  private Car car;
  private CarService carService;

  @Before
  public void setUp() throws Exception {
    carDto = new CarDto();
    car = new Car();
    carRepository = mock(CarRepository.class);
    carFactory = mock(CarFactory.class);
    carService = new CarService(carFactory, carRepository);
  }

  @Test
  public void whenCreatingCar_shouldCallFactory() {
    carService.createCar(carDto);

//    verify(carFactory, times(1)).create(carDto.brand, carDto.model, carDto.year, carDto.licensePlate, carDto.userId);
  }

//  @Test
//  public void whenCreatingCar_shouldSaveCarInRepo() {
//    given(carFactory.create(carDto.brand, carDto.model, carDto.year, carDto.licensePlate)).willReturn(car);
//
//    carService.createCar(carDto);
//
//    verify(carRepository, times(1)).save(this.car);
//  }

  @Test
  public void whenCreatingCar_shouldCallUserServiceToValidateUser() {

  }
}