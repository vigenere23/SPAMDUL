package ca.ulaval.glo4003.spamdul.usecases.pass;

import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassFactory;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.sale.PassSender;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.CampusAccessService;

public class PassService {

  private final PassRepository passRepository;
  private final PassFactory passFactory;
  private final CampusAccessService campusAccessService;
  private final PassSender passSender;

  public PassService(PassRepository passRepository,
                     PassFactory passFactory,
                     CampusAccessService campusAccessService,
                     PassSender passSender) {
    this.passRepository = passRepository;
    this.passFactory = passFactory;
    this.campusAccessService = campusAccessService;
    this.passSender = passSender;
  }

  public void createPass(PassDto dto) {
    Pass pass = passFactory.create(dto.parkingZone, dto.timePeriodDto);
    campusAccessService.associatePassToCampusAccess(dto.campusAccessCode, pass.getPassCode(), pass.getTimePeriod());
    passRepository.save(pass);

    passSender.sendPass(dto.deliveryDto, pass.getPassCode());
  }
}
