package ca.ulaval.glo4003.spamdul.entity.infractions;

import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public class InfractionDto {

  public InfractionId id;
  public Amount amount;
  public InfractionCode code;
  public String infractionDescription;
}
