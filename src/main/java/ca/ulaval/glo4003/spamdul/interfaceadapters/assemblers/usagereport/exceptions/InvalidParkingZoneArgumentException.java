package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions;

public class InvalidParkingZoneArgumentException extends InvalidUsageReportArgumentException {

  public InvalidParkingZoneArgumentException() {
    super("The Parking zone provided must be ZONE_*number*");
  }
}
