package ca.ulaval.glo4003.spamdul.infrastructure.db.car;

import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.car.CarRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class InMemoryCarRepository implements CarRepository {

  private static final Map<CarId, Car> cars = new HashMap<>();
  private static final Logger logger = Logger.getLogger(InMemoryCarRepository.class.getName());

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