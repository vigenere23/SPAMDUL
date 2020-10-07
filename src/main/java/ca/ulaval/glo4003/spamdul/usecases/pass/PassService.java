package ca.ulaval.glo4003.spamdul.usecases.pass;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.pass.*;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.CampusAccessService;

import java.time.DayOfWeek;

public class PassService {

  private final PassRepository passRepository;
  private final PassFactory passFactory;
  private final CampusAccessService campusAccessService;

  public PassService(PassRepository passRepository, PassFactory passFactory, CampusAccessService campusAccessService) {
    this.passRepository = passRepository;
    this.passFactory = passFactory;
    this.campusAccessService = campusAccessService;
  }

  public PassCode createPass(CampusAccessCode campusAccessCode, ParkingZone parkingZone, TimePeriodDto timePeriodDto) {
    Pass pass = passFactory.create(parkingZone, timePeriodDto);
    campusAccessService.associatePassToCampusAccess(campusAccessCode, pass.getPassCode(), pass.getTimePeriod());
    passRepository.save(pass);

    return pass.getPassCode();
  }
}
