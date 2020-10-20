package ca.ulaval.glo4003.spamdul.usecases.infraction;

import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.ValidationChain;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;

public class InfractionService {

  private InfractionRepository infractionRepository;
  private PassRepository passRepository;
  private ValidationChain validationChain;

  public InfractionService(InfractionRepository infractionRepository,
                           PassRepository passRepository,
                           ValidationChain validationChain) {
    this.infractionRepository = infractionRepository;
    this.passRepository = passRepository;
    this.validationChain = validationChain;
  }

  public Infraction validatePass(InfractionValidationDto infractionValidationDto) {
    Pass pass = passRepository.findByPassCode(infractionValidationDto.passCode);
    try {
      validationChain.validate(pass, infractionValidationDto.parkingZone, infractionValidationDto.time);
    } catch (InfractionException e) {
      return infractionRepository.findBy(InfractionCode.valueOf(e.getMessage()));
    }
    return null;
  }

  public Infraction createNoPassInfraction() {
    return infractionRepository.findBy(InfractionCode.valueOf("VIG_03"));
  }

  public Infraction createInvalidPassException() {
    return infractionRepository.findBy(InfractionCode.valueOf("VIG_02"));
  }
}