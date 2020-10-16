package ca.ulaval.glo4003.spamdul.entity.infractions;

public class Infraction {

  private String infractionDscription;
  private InfractionCode code;
  private double amount;

  public Infraction(String infractionDscription, InfractionCode code, double amount) {
    this.infractionDscription = infractionDscription;
    this.code = code;
    this.amount = amount;
  }

  public String getInfractionDscription() {
    return infractionDscription;
  }

  public InfractionCode getCode() {
    return code;
  }

  public double getAmount() {
    return amount;
  }
}
