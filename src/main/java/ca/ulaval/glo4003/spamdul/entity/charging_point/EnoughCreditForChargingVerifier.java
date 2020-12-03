package ca.ulaval.glo4003.spamdul.entity.charging_point;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.RechargULCardNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserNotFoundException;

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
