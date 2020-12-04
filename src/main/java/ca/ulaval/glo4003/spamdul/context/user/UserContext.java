package ca.ulaval.glo4003.spamdul.context.user;

import ca.ulaval.glo4003.spamdul.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.user.car.CarFactory;
import ca.ulaval.glo4003.spamdul.entity.user.UserFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.db.user.InMemoryUserRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.user.UserResource;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess.car.CarAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.UserAssembler;
import ca.ulaval.glo4003.spamdul.usecases.user.UserService;
import ca.ulaval.glo4003.spamdul.utils.InstanceMap;

public class UserContext implements ResourceContext {

  private final UserRepository userRepository;
  private final UserResource userResource;

  public UserContext() {
    userRepository = new InMemoryUserRepository();
    CarAssembler carAssembler = new CarAssembler();
    UserAssembler userAssembler = new UserAssembler(carAssembler);
    CarFactory carFactory = new CarFactory();
    UserFactory userFactory = new UserFactory(carFactory);
    userResource = new UserResource(userAssembler, new UserService(userRepository, userFactory));
  }

  public UserRepository getUserRepository() {
    return userRepository;
  }

  public void registerResources(InstanceMap resources) {
   resources.add(userResource);
  }
}
