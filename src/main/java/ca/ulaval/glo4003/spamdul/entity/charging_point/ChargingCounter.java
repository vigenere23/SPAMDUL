package ca.ulaval.glo4003.spamdul.entity.charging_point;

import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

public class ChargingCounter {

  private final Amount hourlyFee = Amount.valueOf(1);

  private final RechargULCard rechargULCard;
  private Optional<LocalDateTime> startTime = Optional.empty();

  public ChargingCounter(RechargULCard rechargULCard) {
    this.rechargULCard = rechargULCard;
  }

  public void start() {
    verifyNotStarted();
    verifyEnoughFunds();

    startTime = Optional.of(LocalDateTime.now());
  }

  public void stop() {
    verifyStarted();

    LocalDateTime endTime = LocalDateTime.now();
    long hours = Duration.between(startTime.get(), endTime).toHours();
    Amount total = hourlyFee.multiply(hours);
    rechargULCard.debit(total);

    startTime = Optional.empty();
  }

  private void verifyEnoughFunds() {
    if (rechargULCard.hasUnpaidCharges()) {
      throw new RuntimeException("the rechargUL card has insufficient funds");
    }
  }

  private void verifyNotStarted() {
    if (startTime.isPresent()) {
      throw new RuntimeException("a charging is already ongoing");
    }
  }

  private void verifyStarted() {
    if (!startTime.isPresent()) {
      throw new RuntimeException("no charging was started");
    }
  }
}
