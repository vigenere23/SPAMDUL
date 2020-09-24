package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

public class ParkingAccessLog {
    private ParkingZone zone;
    private LocalDate accessDate;

    public ParkingAccessLog(ParkingZone zone, LocalDate accessDate) {
        this.zone = zone;
        this.accessDate = accessDate;
    }
}
