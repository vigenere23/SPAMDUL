package ca.ulaval.glo4003.spamdul.parking2.entities.infraction;

public enum InfractionType {
  NO_PERMIT("VIG_03"),
  INVALID_PERMIT("VIG_02"),
  CAR_MISMATCH("VIG_04"),
  RESERVED_FOR_PLUGGED_ELECTRIC("ZONE_03"),
  RESERVED_FOR_ELECTRIC("ZONE_02"),
  INVALID_ZONE("ZONE_01"),
  INVALID_DAY("VIG_01"),
  INVALID_TIME("TEMPS_01");

  private final String value;

  InfractionType(String value) {
    this.value = value;
  }
}
