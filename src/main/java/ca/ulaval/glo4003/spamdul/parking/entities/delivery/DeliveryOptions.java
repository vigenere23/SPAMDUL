package ca.ulaval.glo4003.spamdul.parking.entities.delivery;

import ca.ulaval.glo4003.spamdul.parking.entities.delivery.email.EmailAddress;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.post.PostalAddress;

public class DeliveryOptions {

  public EmailAddress emailAddress;
  public String subject;
  public PostalAddress postalAddress;
}
