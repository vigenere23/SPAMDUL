package ca.ulaval.glo4003.spamdul.parking2.api.assemblers;

import ca.ulaval.glo4003.spamdul.parking2.api.dtos.ParkingAccessRequest;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitNumber;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.ParkingAccessDto;
import java.time.LocalDateTime;

public class ParkingAccessRequestAssembler {

  public ParkingAccessDto fromRequest(ParkingAccessRequest request) {
    ParkingAccessDto dto = new ParkingAccessDto();
    dto.accessDateTime = LocalDateTime.now();

    if (request.parkingZone != null) {
      dto.parkingZone = ParkingZone.valueOf(request.parkingZone.toUpperCase());
    } else {
      dto.parkingZone = ParkingZone.ANY;
    }

    if (request.permitNumber != null) {
      dto.permitNumber = PermitNumber.valueOf(request.permitNumber);
    }

    if (request.licensePlate != null) {
      dto.licensePlate = LicensePlate.valueOf(request.licensePlate);
    }

    return dto;
  }

  public ParkingAccessDto fromBikeRequest(ParkingAccessRequest request) {
    ParkingAccessDto dto = fromRequest(request);
    dto.parkingZone = ParkingZone.BIKE;
    
    return dto;
  }
}
