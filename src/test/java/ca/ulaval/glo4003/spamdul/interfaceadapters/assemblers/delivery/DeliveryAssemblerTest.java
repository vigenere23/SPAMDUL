package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryMode;
import ca.ulaval.glo4003.spamdul.entity.delivery.post.PostalAddress;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.dto.DeliveryRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.exceptions.InvalidDeliveryModeException;
import ca.ulaval.glo4003.spamdul.usecases.sale.DeliveryDto;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class DeliveryAssemblerTest {

    private final String A_DELIVER_MODE_STRING = "email";
    private final DeliveryMode A_DELIVERY_MODE = DeliveryMode.EMAIL;
    private final String A_EMAIL_ADDRESS = "test@test.ca";
    private final String A_POSTAL_ADDRESS_STRING = "test address";
    private final PostalAddress A_POSTAL_ADDRESS = new PostalAddress(A_POSTAL_ADDRESS_STRING);

    private DeliveryRequest A_DELIVERY_REQUEST = new DeliveryRequest();
    private DeliveryAssembler deliveryAssembler = new DeliveryAssembler();

    @Before
    public void setUp() {
        A_DELIVERY_REQUEST.deliveryMode = A_DELIVER_MODE_STRING;
        A_DELIVERY_REQUEST.emailAddress = A_EMAIL_ADDRESS;
        A_DELIVERY_REQUEST.postalAddress = A_POSTAL_ADDRESS_STRING;

    }

    @Test
    public void whenCreatingFromDto_thenShouldCreateDeliveryDtoWithRightFields() {
        DeliveryDto deliveryDto = deliveryAssembler.fromDto(A_DELIVERY_REQUEST);

        assertThat(deliveryDto.emailAddress).isEqualTo(A_EMAIL_ADDRESS);
        assertThat(deliveryDto.postalAddress).isEqualTo(A_POSTAL_ADDRESS);
        assertThat(deliveryDto.deliveryMode).isEqualTo(A_DELIVERY_MODE);
    }

    @Test(expected = InvalidDeliveryModeException.class)
    public void givenInvalidDeliveryMode_whenCreatingFromDto_thenShouldThrowInvalidDeliveryModeException() {
        A_DELIVERY_REQUEST.deliveryMode = "test";

        deliveryAssembler.fromDto(A_DELIVERY_REQUEST);
    }
}
