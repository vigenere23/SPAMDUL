package ca.ulaval.glo4003.spamdul.parking.entities.delivery;

import ca.ulaval.glo4003.spamdul.parking.entities.delivery.email.EmailDeliverer;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.post.PostalDeliverer;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.sspoffice.SSPOfficeDeliverer;

public class DeliveryStrategyFactory {

  private final EmailDeliverer emailDeliverer;
  private final PostalDeliverer postalDeliverer;
  private final SSPOfficeDeliverer sspOfficeDeliverer;

  public DeliveryStrategyFactory(EmailDeliverer emailDeliverer,
                                 PostalDeliverer postalDeliverer,
                                 SSPOfficeDeliverer sspOfficeDeliverer) {
    this.emailDeliverer = emailDeliverer;
    this.postalDeliverer = postalDeliverer;
    this.sspOfficeDeliverer = sspOfficeDeliverer;
  }

  public DeliveryStrategy create(DeliveryMode deliveryMode) {
    if (deliveryMode == DeliveryMode.EMAIL) {
      return emailDeliverer;

    } else if (deliveryMode == DeliveryMode.POST) {
      return postalDeliverer;

    } else if (deliveryMode == DeliveryMode.SSP_OFFICE) {
      return sspOfficeDeliverer;

    } else {
      throw new InvalidDeliveryModeException();
    }
  }
}
