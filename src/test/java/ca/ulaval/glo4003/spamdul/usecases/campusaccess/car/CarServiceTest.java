//package ca.ulaval.glo4003.spamdul.usecases.campusaccess.car;
//
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//import ca.ulaval.glo4003.spamdul.entity.car.Car;
//import ca.ulaval.glo4003.spamdul.entity.car.CarFactory;
//import ca.ulaval.glo4003.spamdul.entity.car.CarId;
//import ca.ulaval.glo4003.spamdul.entity.car.CarType;
//import ca.ulaval.glo4003.spamdul.entity.car.LicensePlate;
//import org.junit.Before;
//import org.junit.Test;
//
//public class CarServiceTest {
//
//  private final String A_CAR_BRAND = "brand";
//  private final String A_CAR_MODEL = "model";
//  private final String A_LICENSE_PLATE_STRING = "license plate";
//  private final LicensePlate A_LICENSE_PLATE = new LicensePlate(A_LICENSE_PLATE_STRING);
//  private final CarId A_CAR_ID = CarId.valueOf("1");
//  private final CarType A_CAR_TYPE = CarType.ECONOMIQUE;
//  private final int A_YEAR = 2020;
//
//  private CarFactory carFactory;
//  private CarDto carDto;
//  private Car car;
//  private CarService carService;
//
//  @Before
//  public void setUp() throws Exception {
//    carDto = new CarDto();
//    car = new Car(A_CAR_ID, A_CAR_TYPE, A_CAR_BRAND, A_CAR_MODEL, A_YEAR, A_LICENSE_PLATE);
//    carFactory = mock(CarFactory.class);
//    carService = new CarService(carFactory);
//  }
//
//  @Test
//  public void whenCreatingCar_shouldCallFactory() {
//    carService.createCar(carDto);
//
//    verify(carFactory, times(1)).create(carDto.carType, carDto.brand, carDto.model, carDto.year, carDto.licensePlate);
//  }
//}