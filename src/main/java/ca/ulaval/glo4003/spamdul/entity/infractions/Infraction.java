package ca.ulaval.glo4003.spamdul.entity.infractions;

public class Infraction {

  private String infractionDscription;
  private InfractionCode code;
  private int montant;

  public Infraction(String infractionDscription, InfractionCode code, int montant) {
    this.infractionDscription = infractionDscription;
    this.code = code;
    this.montant = montant;
  }

  public String getInfractionDscription() {
    return infractionDscription;
  }

  public InfractionCode getCode() {
    return code;
  }

  public int getMontant() {
    return montant;
  }
}
