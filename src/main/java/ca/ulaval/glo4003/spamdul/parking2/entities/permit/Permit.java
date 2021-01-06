package ca.ulaval.glo4003.spamdul.parking2.entities.permit;

import ca.ulaval.glo4003.spamdul.invoice.entities.Priceable;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.time.LocalDateTime;

public abstract class Permit implements Priceable {

  protected final PermitNumber permitNumber;

  public Permit(PermitNumber permitNumber) {
    this.permitNumber = permitNumber;
  }

  public abstract void validateAccess(LocalDateTime accessDateTime, ParkingZone parkingZone);

  public PermitNumber getPermitNumber() {
    return permitNumber;
  }

  @Override
  public Amount getPrice() {
    // TODO
    return Amount.valueOf(20.5);
  }
}
