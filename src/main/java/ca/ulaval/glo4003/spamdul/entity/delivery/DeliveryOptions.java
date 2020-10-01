package ca.ulaval.glo4003.spamdul.entity.delivery;

import ca.ulaval.glo4003.spamdul.entity.delivery.email.EmailAddress;
import ca.ulaval.glo4003.spamdul.entity.delivery.post.PostalAddress;

public class DeliveryOptions {
    public EmailAddress emailAddress;
    public String subject;
    public PostalAddress postalAddress;
}
