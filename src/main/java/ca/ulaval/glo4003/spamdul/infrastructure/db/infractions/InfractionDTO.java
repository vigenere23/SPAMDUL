package ca.ulaval.glo4003.spamdul.infrastructure.db.infractions;

public class InfractionDTO {
  public String infraction;
  public String code;
  public int montant;

  public InfractionDTO() {
  }

  public String getInfraction() {
    return infraction;
  }

  public void setInfraction(String infraction) {
    this.infraction = infraction;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public int getMontant() {
    return montant;
  }

  public void setMontant(int montant) {
    this.montant = montant;
  }
}
