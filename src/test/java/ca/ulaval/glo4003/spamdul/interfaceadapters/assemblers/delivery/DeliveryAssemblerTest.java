package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryMode;
import ca.ulaval.glo4003.spamdul.entity.delivery.email.EmailAddress;
import ca.ulaval.glo4003.spamdul.entity.delivery.post.PostalAddress;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.pass.dto.DeliveryRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.pass.dto.PostalAddressRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.exceptions.InvalidDeliveryModeException;
import ca.ulaval.glo4003.spamdul.usecases.pass.DeliveryDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeliveryAssemblerTest {

  private final static String A_EMAIL_ADDRESS_STRING = "test@test.ca";

  private DeliveryRequest deliveryRequest = new DeliveryRequest();
  private PostalAddressRequest postalAddressRequest = new PostalAddressRequest();
  private EmailAddress emailAddress = new EmailAddress("");
  private PostalAddress postalAddress = new PostalAddress("", "", "", "", "", "", "");

  @Mock
  private EmailAddressAssembler emailAddressAssembler;
  @Mock
  private PostalAddressAssembler postalAddressAssembler;

  private DeliveryAssembler deliveryAssembler;

  @Before
  public void setUp() {
    deliveryAssembler = new DeliveryAssembler(emailAddressAssembler, postalAddressAssembler);
  }

  @Test
  public void givenEmailDelivery_whenCreatingFromDto_thenShouldCallEmailAddressAssembler() {
    when(emailAddressAssembler.fromString(A_EMAIL_ADDRESS_STRING)).thenReturn(emailAddress);
    deliveryRequest.deliveryMode = "email";
    deliveryRequest.emailAddress = A_EMAIL_ADDRESS_STRING;

    DeliveryDto deliveryDto = deliveryAssembler.fromRequest(deliveryRequest);

    verify(emailAddressAssembler).fromString(A_EMAIL_ADDRESS_STRING);
    assertThat(deliveryDto.deliveryMode).isEqualTo(DeliveryMode.EMAIL);
    assertThat(deliveryDto.emailAddress).isEqualTo(emailAddress);
  }

  @Test
  public void givenPostDelivery_whenCreatingFromDto_thenShouldCallPostAddressAssembler() {
    when(postalAddressAssembler.fromDto(postalAddressRequest)).thenReturn(postalAddress);
    deliveryRequest.deliveryMode = "post";
    deliveryRequest.postalAddress = postalAddressRequest;

    DeliveryDto deliveryDto = deliveryAssembler.fromRequest(deliveryRequest);

    verify(postalAddressAssembler).fromDto(postalAddressRequest);
    assertThat(deliveryDto.deliveryMode).isEqualTo(DeliveryMode.POST);
    assertThat(deliveryDto.postalAddress).isEqualTo(postalAddress);
  }

  @Test(expected = InvalidDeliveryModeException.class)
  public void givenInvalidDeliveryMode_whenCreatingFromDto_thenShouldThrowInvalidDeliveryModeException() {
    deliveryRequest.deliveryMode = "test";

    deliveryAssembler.fromRequest(deliveryRequest);
  }

  @Test
  public void givenSSPOfficeDeliveryMode_whenCreatingFromRequest_thenSSPOfficeDeliveryAssembler() {
    deliveryRequest.deliveryMode = "ssp_office";

    DeliveryDto deliveryDto = deliveryAssembler.fromRequest(deliveryRequest);

    verify(emailAddressAssembler, never()).fromString(anyString());
    verify(postalAddressAssembler, never()).fromDto(any());
    assertThat(deliveryDto.deliveryMode).isEqualTo(DeliveryMode.SSP_OFFICE);
  }
}
