package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import java.time.LocalDate;

public class ParkingAccessLog {
    private ParkingAccessLogId id;
    private ParkingZone zone;
    private LocalDate accessDate;

    public ParkingAccessLog(ParkingAccessLogId id, ParkingZone zone, LocalDate accessDate) {
        this.id = id;
        this.zone = zone;
        this.accessDate = accessDate;
    }

    public ParkingZone getZone() {
        return zone;
    }

    public LocalDate getAccessDate() {
        return accessDate;
    }

    public ParkingAccessLogId getId() {
        return id;
    }
}
