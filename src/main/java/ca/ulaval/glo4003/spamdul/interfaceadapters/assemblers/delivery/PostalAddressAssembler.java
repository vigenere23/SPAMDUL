package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery;

import ca.ulaval.glo4003.spamdul.entity.delivery.post.PostalAddress;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.dto.PostalAddressRequest;

public class PostalAddressAssembler {
    public PostalAddress fromDto(PostalAddressRequest postalAddressRequest) {
        return new PostalAddress(postalAddressRequest.name,
                postalAddressRequest.line1,
                postalAddressRequest.line2,
                postalAddressRequest.city,
                postalAddressRequest.province,
                postalAddressRequest.postalCode,
                postalAddressRequest.country);
    }
}
