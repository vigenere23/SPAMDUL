package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale;

import ca.ulaval.glo4003.spamdul.entity.post.PostalAddress;
import ca.ulaval.glo4003.spamdul.entity.sale.DeliveryMode;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.dto.SaleRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.pass.PassAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidDeliveryModeException;
import ca.ulaval.glo4003.spamdul.usecases.sale.SaleDto;

public class SaleAssembler {

  private PassAssembler passAssembler;

  public SaleAssembler(PassAssembler passAssembler) {
    this.passAssembler = passAssembler;
  }

  public SaleDto fromDto(SaleRequest saleRequest) {
    SaleDto saleDto = new SaleDto();

    saleDto.deliveryMode = getDeliveryMode(saleRequest.deliveryMode);
    saleDto.emailAddress = saleRequest.emailAddress;
    saleDto.postalAddress = new PostalAddress(saleRequest.postalAddress);
    saleDto.passDTO = passAssembler.fromDTO(saleRequest.pass);

    return saleDto;
  }

  private DeliveryMode getDeliveryMode(String deliveryMode) {
    try {
      return DeliveryMode.valueOf(deliveryMode.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new InvalidDeliveryModeException("The delivery is either made by post or by mail");
    }
  }
}
