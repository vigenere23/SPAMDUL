package ca.ulaval.glo4003.spamdul.usecases.sale;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.sale.DeliveryMode;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;

public class PassDto {
    private ParkingZone parkingZone;
    private int numberOfTerms;
    private DeliveryMode deliveryMode;
    private UserId userId;
}
