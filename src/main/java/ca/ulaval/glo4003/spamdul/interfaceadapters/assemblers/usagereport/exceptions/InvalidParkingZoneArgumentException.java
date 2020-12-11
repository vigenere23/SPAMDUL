package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions;

public class InvalidParkingZoneArgumentException extends InvalidUsageReportArgumentException {

  public String getError() {
    return "INVALID_PARKING_ZONE";
  }

  public String getDescription() {
    return "The Parking zone provided must be ZONE_*number*";
  }
}
