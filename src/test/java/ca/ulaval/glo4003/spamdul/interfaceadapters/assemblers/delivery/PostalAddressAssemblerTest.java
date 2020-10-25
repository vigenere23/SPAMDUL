package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.delivery.post.PostalAddress;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.pass.dto.PostalAddressRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.exceptions.InvalidPostalAddressException;
import org.junit.Before;
import org.junit.Test;

public class PostalAddressAssemblerTest {

  private PostalAddressRequest postalAddressRequest;

  private PostalAddressAssembler postalAddressAssembler;

  @Before
  public void setUp() {
    postalAddressAssembler = new PostalAddressAssembler();
    postalAddressRequest = new PostalAddressRequest();
    postalAddressRequest.name = "jg";
    postalAddressRequest.line1 = "kj";
    postalAddressRequest.line2 = "tth";
    postalAddressRequest.city = "hth";
    postalAddressRequest.country = "jhf";
    postalAddressRequest.postalCode = "f";
    postalAddressRequest.province = "jfeko";
  }

  @Test
  public void whenAssemblingPostalAddress_shouldCreatePostalAddressWithRightFields() {
    PostalAddress postalAddress = postalAddressAssembler.fromDto(postalAddressRequest);

    assertThat(postalAddress.getLine1()).isEqualTo(postalAddressRequest.line1);
    assertThat(postalAddress.getLine2()).isEqualTo(postalAddressRequest.line2);
    assertThat(postalAddress.getCity()).isEqualTo(postalAddressRequest.city);
    assertThat(postalAddress.getProvince()).isEqualTo(postalAddressRequest.province);
    assertThat(postalAddress.getPostalCode()).isEqualTo(postalAddressRequest.postalCode);
    assertThat(postalAddress.getCountry()).isEqualTo(postalAddressRequest.country);
  }

  @Test(expected = InvalidPostalAddressException.class)
  public void givenNullField_whenAssemblingPostalAddress_shouldThrowInvalidPostalAddressException() {
    postalAddressRequest.country = null;
    postalAddressAssembler.fromDto(postalAddressRequest);
  }
}
