package ca.ulaval.glo4003.spamdul.parking.context.bikeparkingaccess;

import ca.ulaval.glo4003.spamdul.assemblers.parking.bikeparking.BikeParkingAccessAssembler;
import ca.ulaval.glo4003.spamdul.parking.api.bikeparkingaccess.BikeParkingAccessResource;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserRepository;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.bike.BikeParkingAccessValidator;
import ca.ulaval.glo4003.spamdul.parking.usecases.bikeparkingaccess.BikeParkingAccessUseCase;
import ca.ulaval.glo4003.spamdul.shared.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.time.infrastructure.calendar.HardCodedCalendar;
import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLogger;

public class BikeParkingAccessContext implements ResourceContext {

  private final BikeParkingAccessResource bikeParkingAccessResource;

  public BikeParkingAccessContext(UserRepository userRepository, ParkingAccessLogger parkingAccessLogger) {
    Calendar calendar = new HardCodedCalendar();
    BikeParkingAccessValidator bikeParkingAccessValidator = new BikeParkingAccessValidator(calendar);
    BikeParkingAccessAssembler bikeParkingAccessAssembler = new BikeParkingAccessAssembler();
    BikeParkingAccessUseCase bikeParkingAccessUseCase = new BikeParkingAccessUseCase(userRepository,
                                                                                     bikeParkingAccessValidator,
                                                                                     calendar);
    bikeParkingAccessUseCase.register(parkingAccessLogger);
    bikeParkingAccessResource = new BikeParkingAccessResource(bikeParkingAccessUseCase,
                                                              bikeParkingAccessAssembler);
  }

  @Override public void registerResources(InstanceMap instanceMap) {
    instanceMap.add(bikeParkingAccessResource);
  }
}
