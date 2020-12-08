package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.bikeparking;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.bikeparkingaccess.dto.BikeParkingAccessResponse;

public class BikeParkingAccessAssembler {

  public BikeParkingAccessResponse toResponse(boolean canAccessParking) {
    BikeParkingAccessResponse bikeParkingAccessResponse = new BikeParkingAccessResponse();
    if (canAccessParking) {
      bikeParkingAccessResponse.access = "GRANTED";
    } else {
      bikeParkingAccessResponse.access = "NOT_GRANTED";
    }

    return bikeParkingAccessResponse;
  }
}
