package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery;

import ca.ulaval.glo4003.spamdul.entity.delivery.post.PostalAddress;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.dto.PostalAddressRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.exceptions.InvalidPostalAddressException;

public class PostalAddressAssembler {

  public PostalAddress fromDto(PostalAddressRequest postalAddressRequest) {
    if (postalAddressRequest.name == null ||
        postalAddressRequest.line1 == null ||
        postalAddressRequest.city == null ||
        postalAddressRequest.province == null ||
        postalAddressRequest.postalCode == null ||
        postalAddressRequest.country == null) {
      throw new InvalidPostalAddressException("Only line 2 field is optional in postal address");
    }
    return new PostalAddress(postalAddressRequest.name,
                             postalAddressRequest.line1,
                             postalAddressRequest.line2,
                             postalAddressRequest.city,
                             postalAddressRequest.province,
                             postalAddressRequest.postalCode,
                             postalAddressRequest.country);
  }
}
