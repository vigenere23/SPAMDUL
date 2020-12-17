package ca.ulaval.glo4003.spamdul.context.user;

import ca.ulaval.glo4003.spamdul.api.user.UserResource;
import ca.ulaval.glo4003.spamdul.assemblers.parking.campusaccess.car.CarAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.user.UserAssembler;
import ca.ulaval.glo4003.spamdul.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.entity.user.UserFactory;
import ca.ulaval.glo4003.spamdul.entity.user.UserIdFactory;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.user.car.CarFactory;
import ca.ulaval.glo4003.spamdul.entity.user.car.CarIdFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.db.user.InMemoryUserRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.ids.IncrementalIdGenerator;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import ca.ulaval.glo4003.spamdul.usecases.user.UserUseCase;

public class UserContext implements ResourceContext {

  private final UserRepository userRepository;
  private final UserResource userResource;
  private final CarFactory carFactory;

  public UserContext() {
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
