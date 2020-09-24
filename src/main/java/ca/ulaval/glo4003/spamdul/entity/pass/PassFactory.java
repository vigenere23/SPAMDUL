package ca.ulaval.glo4003.spamdul.entity.pass;

import ca.ulaval.glo4003.spamdul.entity.calendar.Calendar;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;

import java.time.LocalDate;

public class PassFactory {
    Calendar calendar;

    public PassFactory(Calendar calendar) {
        this.calendar = calendar;
    }

    public Pass create(UserId userId, ParkingZone parkingZone, int numberOfTerms) {
        if (numberOfTerms > 3) {
            throw new InvalidNumberOfTermsException("The maximum number of terms for a pass is three.");
        }

        LocalDate firstDayOfValidity = calendar.getCurrentTermStartDate();
        LocalDate lastDayOfValidity = calendar.getEndOfTermDateInNTerms(numberOfTerms);
        PassCode passCode = new PassCode();

        return new Pass(passCode, userId, parkingZone, firstDayOfValidity, lastDayOfValidity);
    }
}
