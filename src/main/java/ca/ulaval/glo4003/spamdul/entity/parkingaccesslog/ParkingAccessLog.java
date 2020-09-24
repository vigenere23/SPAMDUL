package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import java.time.LocalDate;

public class ParkingAccessLog {
    private ParkingZone zone;
    private LocalDate accessDate;

    public ParkingAccessLog(ParkingZone zone, LocalDate accessDate) {
        this.zone = zone;
        this.accessDate = accessDate;
    }

    public ParkingZone getZone() {
        return zone;
    }

    public LocalDate getAccessDate() {
        return accessDate;
    }
}
