package ca.ulaval.glo4003.spamdul.usecases.pass;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.PassType;
import ca.ulaval.glo4003.spamdul.entity.sale.DeliveryMode;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import java.time.DayOfWeek;

public class PassDto {
    public ParkingZone parkingZone;
    public PassType passType;
    public UserId userId;
}
