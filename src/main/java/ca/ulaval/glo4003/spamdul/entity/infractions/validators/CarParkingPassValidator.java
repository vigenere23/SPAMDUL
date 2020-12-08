package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.PassRepositoryNotSetException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import java.util.HashMap;
import java.util.Map;

public abstract class CarParkingPassValidator {

  private static UserRepository userRepository;
  protected final static Map<CarParkingPassCode, CarParkingPass> passCache = new HashMap<>();
  protected CarParkingPassValidator nextCarParkingPassValidator;


  public static void setPassRepository(UserRepository userRepository) {
    CarParkingPassValidator.userRepository = userRepository;
  }

  public void setNextValidator(CarParkingPassValidator carParkingPassValidator) {
    nextCarParkingPassValidator = carParkingPassValidator;
  }

  public abstract void validate(PassToValidateDto passToValidateDto);

  protected void nextValidation(PassToValidateDto passToValidateDto) {
    if (nextCarParkingPassValidator != null) {
      nextCarParkingPassValidator.validate(passToValidateDto);
    } else {
      terminateChainOfValidation(CarParkingPassCode.valueOf(passToValidateDto.passCode));
    }
  }

  private void terminateChainOfValidation(ParkingPassCode parkingPassCode) {
    passCache.remove(parkingPassCode);
  }

  protected CarParkingPass getCorrespondingPass(CarParkingPassCode parkingPassCode) {
    CarParkingPass correspondingParkingPass = passCache.get(parkingPassCode);

    if (correspondingParkingPass == null) {
      if (userRepository == null) {
        throw new PassRepositoryNotSetException("This validator requires pass repository " +
                                                    "to be set before usage");
      }

      correspondingParkingPass = userRepository.findBy(parkingPassCode).getCarParkingPass();
      passCache.put(parkingPassCode, correspondingParkingPass);
    }

    return correspondingParkingPass;
  }
}
