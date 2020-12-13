package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingCategory;
import java.util.Arrays;

public class InvalidParkingCategoryArgumentException extends InvalidUsageReportArgumentException {

  public InvalidParkingCategoryArgumentException() {
    super("Parking category must be one of the following: " + Arrays.toString(ParkingCategory.values()));
  }
}
