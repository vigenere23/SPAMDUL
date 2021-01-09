package ca.ulaval.glo4003.spamdul.parking2.entities.infraction;

import java.util.Arrays;

public enum InfractionType {
  NO_PERMIT("VIG_03"),
  INVALID_PERMIT("VIG_02"),
  CAR_MISMATCH("VIG_04"),
  RESERVED_FOR_PLUGGED_ELECTRIC("ZONE_03"),
  RESERVED_FOR_ELECTRIC("ZONE_02"),
  INVALID_ZONE("ZONE_01"),
  INVALID_DAY("VIG_01"),
  INVALID_TIME("TEMPS_01");

  private final String code;

  InfractionType(String code) {
    this.code = code;
  }

  public static InfractionType fromCode(String code) {
    return Arrays.stream(values())
                 .filter(value -> value.code.equals(code.toUpperCase()))
                 .findFirst()
                 .orElseThrow(() -> new IllegalArgumentException(String.format("No value for %s.%s",
                                                                               InfractionType.class.getName(),
                                                                               code)));
  }

  public String getCode() {
    return code;
  }
}
