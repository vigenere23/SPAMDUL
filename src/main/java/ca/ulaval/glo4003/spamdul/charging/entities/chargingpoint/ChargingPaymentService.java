package ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint;

import ca.ulaval.glo4003.spamdul.charging.entities.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.User;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserRepository;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.util.concurrent.TimeUnit;

public class ChargingPaymentService {

  private final Amount timeUnitFee;
  private final TimeUnit timeUnit;
  private final UserRepository userRepository;

  public ChargingPaymentService(Amount timeUnitFee, TimeUnit timeUnit, UserRepository userRepository) {
    this.timeUnitFee = timeUnitFee;
    this.timeUnit = timeUnit;
    this.userRepository = userRepository;
  }

  public void pay(long milliseconds, RechargULCardId rechargULCardId) {
    if (milliseconds <= 0) {
      return;
    }

    long duration = timeUnit.convert(milliseconds, TimeUnit.MILLISECONDS) + 1; // upper rounding
    Amount amount = timeUnitFee.multiply(duration);

    User user = userRepository.findBy(rechargULCardId);
    user.payForCharging(amount);
    userRepository.save(user);
  }
}
