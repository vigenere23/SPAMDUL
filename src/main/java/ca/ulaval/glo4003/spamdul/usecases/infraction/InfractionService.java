package ca.ulaval.glo4003.spamdul.usecases.infraction;

import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassValidator;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import java.time.LocalTime;

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
    Infraction infraction = generateInfraction(pass,
                                               infractionValidationDto.parkingZone,
                                               infractionValidationDto.time);

    return infraction;
  }

  private Infraction generateInfraction(Pass pass, ParkingZone parkingZone, LocalTime time) {
    InfractionCode infractionCode = passValidator.validate(pass,
                                                           parkingZone,
                                                           time);
    if (infractionCode != null) {
      return infractionRepository.findBy(infractionCode);
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