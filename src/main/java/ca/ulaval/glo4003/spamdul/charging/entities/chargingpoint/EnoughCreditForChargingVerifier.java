package ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint;

import ca.ulaval.glo4003.spamdul.charging.entities.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.charging.entities.rechargul.exceptions.RechargULCardNotFoundException;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.User;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserRepository;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.exceptions.UserNotFoundException;

public class EnoughCreditForChargingVerifier {

  private final UserRepository userRepository;

  public EnoughCreditForChargingVerifier(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void verify(RechargULCardId rechargULCardId) {
    try {
      User user = userRepository.findBy(rechargULCardId);
      user.verifyEnoughCreditsForCharging();
    } catch (UserNotFoundException e) {
      throw new RechargULCardNotFoundException();
    }
  }
}
