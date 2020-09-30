package ca.ulaval.glo4003.spamdul.usecases.sale;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryMode;
import ca.ulaval.glo4003.spamdul.entity.delivery.post.PostalAddress;

public class DeliveryDto {
    public DeliveryMode deliveryMode;
    public String emailAddress;
    public PostalAddress postalAddress;
}
