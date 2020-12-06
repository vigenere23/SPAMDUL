package ca.ulaval.glo4003.spamdul.entity.infractions;

import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.AlreadyPaidInfractionException;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.util.Objects;

public class Infraction {

  private InfractionId infractionId;
  private String infractionDescription;
  private InfractionCode code;
  private Amount amount;
  private boolean isPaid = false;

  public Infraction(InfractionId infractionId, String infractionDescription, InfractionCode code, Amount amount) {
    this.infractionId = infractionId;
    this.infractionDescription = infractionDescription;
    this.code = code;
    this.amount = amount;
  }

  public Amount pay() {
    if (isPaid) {
      throw new AlreadyPaidInfractionException("Cette infraction a déjà été payée");
    }

    isPaid = true;

    return amount;
  }

  public String getInfractionDescription() {
    return infractionDescription;
  }

  public InfractionId getInfractionId() {
    return infractionId;
  }

  public InfractionCode getCode() {
    return code;
  }

  public Amount getAmount() {
    return amount;
  }

  public boolean isPaid() {
    return isPaid;
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Infraction that = (Infraction) o;
    return amount.equals(((Infraction) o).amount) && isPaid == that.isPaid && Objects.equals(
        infractionId,
        that.infractionId) && Objects.equals(infractionDescription, that.infractionDescription)
        && Objects.equals(code, that.code);
  }

  public int hashCode() {
    return Objects.hash(infractionId, infractionDescription, code, amount, isPaid);
  }
}
