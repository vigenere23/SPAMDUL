package ca.ulaval.glo4003.spamdul.parking.usecases.parkingpass;

import ca.ulaval.glo4003.spamdul.parking.entities.delivery.DeliveryMode;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.email.EmailAddress;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.post.PostalAddress;

public class DeliveryDto {

  public DeliveryMode deliveryMode;
  public EmailAddress emailAddress;
  public PostalAddress postalAddress;
}
