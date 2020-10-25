package ca.ulaval.glo4003.spamdul.entity.infractions;

import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.AlreadyPaidInfractionException;

public class Infraction {

  private InfractionId infractionId;
  private String infractionDescription;
  private InfractionCode code;
  private double amount;
  private boolean isPaid = false;

  public Infraction(InfractionId infractionId, String infractionDescription, InfractionCode code, double amount) {
    this.infractionId = infractionId;
    this.infractionDescription = infractionDescription;
    this.code = code;
    this.amount = amount;
  }

  public void pay() {
    if (isPaid) {
      throw new AlreadyPaidInfractionException("Cette infraction a déjà été payée");
    }

    isPaid = true;
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

  public double getAmount() {
    return amount;
  }

  public boolean isPaid() {
    return isPaid;
  }
}
