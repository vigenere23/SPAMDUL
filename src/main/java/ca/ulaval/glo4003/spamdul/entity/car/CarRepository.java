package ca.ulaval.glo4003.spamdul.entity.car;

public interface CarRepository {

  CarId save(Car car);

  Car findById(CarId carId);
}
