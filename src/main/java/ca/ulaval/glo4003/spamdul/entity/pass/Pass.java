package ca.ulaval.glo4003.spamdul.entity.pass;

import ca.ulaval.glo4003.spamdul.entity.user.UserId;

import java.time.LocalDate;

public class Pass {
    private UserId userId;
    private ParkingZone parkingZone;
    private LocalDate firstDayOfValidity;
    private LocalDate lastDayOfValidity;

    public Pass(UserId userId, ParkingZone parkingZone, LocalDate firstDayOfValidity, LocalDate lastDayOfValidity) {
        this.userId = userId;
        this.parkingZone = parkingZone;
        this.firstDayOfValidity = firstDayOfValidity;
        this.lastDayOfValidity = lastDayOfValidity;
    }

    public UserId getUserId() {
        return userId;
    }

    public ParkingZone getParkingZone() {
        return parkingZone;
    }

    public LocalDate getFirstDayOfValidity() {
        return firstDayOfValidity;
    }

    public LocalDate getLastDayOfValidity() {
        return lastDayOfValidity;
    }
}
