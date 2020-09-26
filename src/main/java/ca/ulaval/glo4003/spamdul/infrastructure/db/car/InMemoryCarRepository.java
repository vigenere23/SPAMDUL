package ca.ulaval.glo4003.spamdul.infrastructure.db.car;

import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.car.CarRepository;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class InMemoryCarRepository implements CarRepository {

  private Map<CarId, Car> cars;
  private final Logger logger = Logger.getLogger(UserRepository.class.getName());

  public InMemoryCarRepository() {
    cars = new HashMap<>();
  }

  public CarId save(Car car) {
    cars.put(car.getCarId(), car);
    String loggingInfos = String.format("Saving car: %s", car.getCarId().toString());
    logger.info(loggingInfos);

    return car.getCarId();
  }

  public Car findById(CarId carId) {
    return cars.get(carId);
  }
}
