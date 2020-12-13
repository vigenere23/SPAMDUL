package ca.ulaval.glo4003.spamdul.context.bikeparkingaccess;

import ca.ulaval.glo4003.spamdul.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.bike.BikeParkingAccessValidator;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.calendar.HardCodedCalendar;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.bikeparking.BikeParkingAccessAssembler;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import ca.ulaval.glo4003.spamdul.ui.bikeparkingaccess.BikeParkingAccessResource;
import ca.ulaval.glo4003.spamdul.usecases.parking.bikeparkingaccess.BikeParkingAccessService;

public class BikeParkingAccessContext implements ResourceContext {

  private final BikeParkingAccessResource bikeParkingAccessResource;

  public BikeParkingAccessContext(UserRepository userRepository) {
    Calendar calendar = new HardCodedCalendar();
    BikeParkingAccessValidator bikeParkingAccessValidator = new BikeParkingAccessValidator(calendar);
    BikeParkingAccessAssembler bikeParkingAccessAssembler = new BikeParkingAccessAssembler();
    BikeParkingAccessService bikeParkingAccessService = new BikeParkingAccessService(userRepository,
                                                                                     bikeParkingAccessValidator);
    bikeParkingAccessResource = new BikeParkingAccessResource(bikeParkingAccessService,
                                                              bikeParkingAccessAssembler);
  }

  @Override public void registerResources(InstanceMap instanceMap) {
    instanceMap.add(bikeParkingAccessResource);
  }
}
