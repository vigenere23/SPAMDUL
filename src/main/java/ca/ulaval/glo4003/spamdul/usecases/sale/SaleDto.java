package ca.ulaval.glo4003.spamdul.usecases.sale;

import ca.ulaval.glo4003.spamdul.entity.post.PostalAddress;
import ca.ulaval.glo4003.spamdul.entity.sale.DeliveryMode;
import ca.ulaval.glo4003.spamdul.usecases.pass.PassDto;

public class SaleDto {

  public DeliveryMode deliveryMode;
  public String emailAddress;
  public PostalAddress postalAddress;
  public PassDto passDTO;
}
