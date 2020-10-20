package ca.ulaval.glo4003.spamdul.usecases.infraction;

import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassValidator;
import ca.ulaval.glo4003.spamdul.entity.pass.InvalidPassCode;
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

    return generateInfraction(infractionValidationDto);
  }

  private Infraction generateInfraction(InfractionValidationDto infractionValidationDto) {
    if (infractionValidationDto.passCode == null) {
      return infractionRepository.findBy(passValidator.validateNoPass());
    } else if (infractionValidationDto.passCode instanceof InvalidPassCode) {
      return infractionRepository.findBy(passValidator.validateInvalidPass());
    } else {
      Pass pass = passRepository.findByPassCode(infractionValidationDto.passCode);
      InfractionCode infractionCode = passValidator.validate(pass,
                                                             infractionValidationDto.parkingZone,
                                                             infractionValidationDto.time);
      if (infractionCode != null) {
        return infractionRepository.findBy(infractionCode);
      }
    }
    return createValidPassInfraction();
  }

  private Infraction createValidPassInfraction() {
    String description = "The pass is valid";
    InfractionCode code = InfractionCode.valueOf("");
    double amount = 0;
    return new Infraction(description, code, amount);
  }


}