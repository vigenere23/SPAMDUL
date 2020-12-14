package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingCategory;
import java.util.Arrays;

public class InvalidParkingCategoryArgumentException extends InvalidUsageReportArgumentException {

  public String getError() {
    return "INVALID_PARKING_CATEGORY";
  }

  public String getDescription() {
    return "Parking category must be one of the following: " + Arrays.toString(ParkingCategory.values());
  }
}
