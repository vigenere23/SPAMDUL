package ca.ulaval.glo4003.spamdul.entity.timeperiod;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.exception.InvalidSeasonException;

public enum Session {
  AUTUMN,
  WINTER,
  SUMMER;

  public static Session parse(String session) {
    switch (session.toUpperCase()) {
      case "A":
      case "AUTUMN":
      case "AUTOMNE":
        return Session.AUTUMN;

      case "H":
      case "WINTER":
      case "HIVER":
        return Session.WINTER;

      case "E":
      case "SUMMER":
      case "ETE":
      case "ÉTÉ":
        return Session.SUMMER;

      default:
        throw new InvalidSeasonException();
    }
  }
}
