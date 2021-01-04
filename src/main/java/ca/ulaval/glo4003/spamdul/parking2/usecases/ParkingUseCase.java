package ca.ulaval.glo4003.spamdul.parking2.usecases;

import ca.ulaval.glo4003.spamdul.parking2.entities.car.Car;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.CarFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.Permit;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitType;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUser;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserId;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserRepository;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.CarCreationDto;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.UserCreationDto;

public class ParkingUseCase {

  private final ParkingUserRepository parkingUserRepository;
  private final ParkingUserFactory parkingUserFactory;
  private final CarFactory carFactory;
  private final PermitFactory permitFactory;

  public ParkingUseCase(ParkingUserRepository parkingUserRepository,
                        ParkingUserFactory parkingUserFactory,
                        CarFactory carFactory,
                        PermitFactory permitFactory) {
    this.parkingUserRepository = parkingUserRepository;
    this.parkingUserFactory = parkingUserFactory;
    this.carFactory = carFactory;
    this.permitFactory = permitFactory;
  }

  public void createUser(UserCreationDto dto) {
    ParkingUser user = parkingUserFactory.create(dto.name, dto.sex, dto.birthDate);
    parkingUserRepository.save(user);
  }

  public void addCarToUser(CarCreationDto dto) {
    ParkingUser parkingUser = parkingUserRepository.findBy(dto.userId);
    Car car = carFactory.create(dto.brand, dto.model, dto.year, dto.licensePlate, dto.carType);
    parkingUser.addCar(car);
    parkingUserRepository.save(parkingUser);
  }

  public void addAccessRight(ParkingUserId parkingUserId, LicensePlate licensePlate/*, ... dto*/) {
    ParkingUser parkingUser = parkingUserRepository.findBy(parkingUserId);
    // TODO
  }

  public void createBikePermit(ParkingUserId parkingUserId) {
    ParkingUser parkingUser = parkingUserRepository.findBy(parkingUserId);
    Permit bikePermit = permitFactory.create(PermitType.BIKE);
    parkingUser.setBikePermit(bikePermit);
    parkingUserRepository.save(parkingUser);
  }
}
