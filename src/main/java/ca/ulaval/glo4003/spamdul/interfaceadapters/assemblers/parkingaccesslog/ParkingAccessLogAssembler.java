package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.usecases.parkingaccesslog.ParkingAccessLogDto;

public class ParkingAccessLogAssembler {
    public ParkingAccessLogDto toDto(ParkingAccessLog parkingAccessLog) {
        return new ParkingAccessLogDto(); // TODO
    }
}
