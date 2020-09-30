package ca.ulaval.glo4003.spamdul.entity.delivery;

import ca.ulaval.glo4003.spamdul.infrastructure.delivery.email.GmailEmailService;
import ca.ulaval.glo4003.spamdul.infrastructure.delivery.post.LoggerPostalService;

public class DeliveryBridgeFactory {

    public DeliveryBridge create(DeliveryMode deliveryMode) {
        if (deliveryMode == DeliveryMode.EMAIL) {
            return new GmailEmailService();
        } else {
            return new LoggerPostalService();
        }
    }
}
