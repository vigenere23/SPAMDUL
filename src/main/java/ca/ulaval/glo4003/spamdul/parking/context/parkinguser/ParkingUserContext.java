package ca.ulaval.glo4003.spamdul.parking.context.parkinguser;

import ca.ulaval.glo4003.spamdul.assemblers.parking.campusaccess.car.CarAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.user.UserAssembler;
import ca.ulaval.glo4003.spamdul.parking.api.parkinguser.UserResource;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserFactory;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserIdFactory;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserRepository;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.CarFactory;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.CarIdFactory;
import ca.ulaval.glo4003.spamdul.parking.infrastructure.persistence.parkinguser.InMemoryUserRepository;
import ca.ulaval.glo4003.spamdul.parking.usecases.parkinguser.UserUseCase;
import ca.ulaval.glo4003.spamdul.shared.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.ids.IncrementalIdGenerator;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;

public class ParkingUserContext implements ResourceContext {

  private final UserRepository userRepository;
  private final UserResource userResource;
  private final CarFactory carFactory;

  public ParkingUserContext() {
    userRepository = new InMemoryUserRepository();
    CarAssembler carAssembler = new CarAssembler();
    UserAssembler userAssembler = new UserAssembler(carAssembler);
    CarIdFactory carIdFactory = new CarIdFactory(new IncrementalIdGenerator());
    carFactory = new CarFactory(carIdFactory);
    UserIdFactory userIdFactory = new UserIdFactory(new IncrementalIdGenerator());
    UserFactory userFactory = new UserFactory(userIdFactory, carFactory);
    userResource = new UserResource(userAssembler, new UserUseCase(userRepository, userFactory));
  }

  public UserRepository getUserRepository() {
    return userRepository;
  }

  public CarFactory getCarFactory() {
    return carFactory;
  }

  @Override public void registerResources(InstanceMap resources) {
    resources.add(userResource);
  }
}
