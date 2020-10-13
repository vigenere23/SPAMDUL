package ca.ulaval.glo4003.spamdul.usecases.InfractionService;

import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassValidator;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;

public class InfractionService {

  private InfractionRepository infractionRepository;
  private PassRepository passRepository;
  private PassValidator passValidator;

  public InfractionService(InfractionRepository infractionRepository,
                           PassRepository passRepository,
                           PassValidator passValidator) {
    this.infractionRepository = infractionRepository;
    this.passRepository = passRepository;
    this.passValidator = passValidator;
  }

  public Infraction validatePass(InfractionValidationDto infractionValidationDto) {
    Pass pass = passRepository.findByPassCode(infractionValidationDto.passCode);
    InfractionCode infractionCode = passValidator.validate(pass,
                                                     infractionValidationDto.parkingZone,
                                                     infractionValidationDto.time);

    return infractionRepository.findBy(infractionCode);
  }
}