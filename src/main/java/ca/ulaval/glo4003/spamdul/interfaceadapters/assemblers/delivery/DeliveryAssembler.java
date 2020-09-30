package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryMode;
import ca.ulaval.glo4003.spamdul.entity.delivery.post.PostalAddress;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.dto.DeliveryRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.exceptions.InvalidDeliveryModeException;
import ca.ulaval.glo4003.spamdul.usecases.sale.DeliveryDto;

public class DeliveryAssembler {

    public DeliveryDto fromDto(DeliveryRequest deliveryRequest) {
        DeliveryDto deliveryDto = new DeliveryDto();

        deliveryDto.deliveryMode = getDeliveryMode(deliveryRequest.deliveryMode);
        deliveryDto.emailAddress = deliveryRequest.emailAddress;
        deliveryDto.postalAddress = new PostalAddress(deliveryRequest.postalAddress);

        return deliveryDto;
    }

    private DeliveryMode getDeliveryMode(String deliveryMode) {
        try {
            return DeliveryMode.valueOf(deliveryMode.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidDeliveryModeException("The delivery is either made by post or by mail");
        }
    }
}
