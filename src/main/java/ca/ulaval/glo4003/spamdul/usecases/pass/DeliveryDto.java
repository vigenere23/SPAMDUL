package ca.ulaval.glo4003.spamdul.usecases.pass;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryMode;
import ca.ulaval.glo4003.spamdul.entity.delivery.email.EmailAddress;
import ca.ulaval.glo4003.spamdul.entity.delivery.post.PostalAddress;

public class DeliveryDto {
    public DeliveryMode deliveryMode;
    public EmailAddress emailAddress;
    public PostalAddress postalAddress;
}
