package ca.ulaval.glo4003.spamdul.entity.infractions;

public class Infraction {

  private InfractionId infractionId;
  private String infractionDescription;
  private InfractionCode code;
  private double amount;
  private boolean isPayed = false;

  public Infraction(InfractionId infractionId, String infractionDescription, InfractionCode code, double amount) {
    this.infractionId = infractionId;
    this.infractionDescription = infractionDescription;
    this.code = code;
    this.amount = amount;
  }

  public void payInfraction(){
    isPayed = true;
  }

  public String getInfractionDscription() {
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
}
