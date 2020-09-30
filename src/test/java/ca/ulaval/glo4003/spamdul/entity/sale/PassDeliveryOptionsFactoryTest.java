package ca.ulaval.glo4003.spamdul.entity.sale;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryMode;
import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryOptions;
import ca.ulaval.glo4003.spamdul.entity.delivery.post.PostalAddress;
import ca.ulaval.glo4003.spamdul.usecases.sale.DeliveryDto;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class PassDeliveryOptionsFactoryTest {
    private static final String A_SUBJECT = "djehfj";
    private static final String A_NAME = "esdj";
    private static final String AN_ADDRESS = "adsjk";
    private static final String AN_EMAIL_ADDRESS = "fdjk";

    private PostalAddress postalAddress;
    private DeliveryDto deliveryDto;

    private PassDeliveryOptionsFactory passDeliveryOptionsFactory;

    @Before
    public void setUpDto() {
        passDeliveryOptionsFactory = new PassDeliveryOptionsFactory();
        deliveryDto = new DeliveryDto();
        deliveryDto.emailAddress = AN_EMAIL_ADDRESS;
        postalAddress = new PostalAddress(AN_ADDRESS);
        deliveryDto.postalAddress = postalAddress;

    }

    @Test
    public void whenCreatingDeliveryOptions_shouldCreateWithRightFields() {
        DeliveryOptions deliveryOptions = passDeliveryOptionsFactory.create(deliveryDto, A_SUBJECT, A_NAME);
        assertThat(deliveryOptions.recipientName).isEqualTo(A_NAME);
        assertThat(deliveryOptions.subject).isEqualTo(A_SUBJECT);
    }

    @Test
    public void givenPostalDelivery_whenCreatingDeliveryOptions_shouldCopyPostalAddress() {
        deliveryDto.deliveryMode = DeliveryMode.POST;
        DeliveryOptions deliveryOptions = passDeliveryOptionsFactory.create(deliveryDto, A_SUBJECT, A_NAME);
        assertThat(deliveryOptions.postalAddress).isEqualTo(postalAddress);
    }

    @Test
    public void givenEmailDelivery_whenCreatingDeliveryOptions_shouldCopyPostalAddress() {
        deliveryDto.deliveryMode = DeliveryMode.EMAIL;
        DeliveryOptions deliveryOptions = passDeliveryOptionsFactory.create(deliveryDto, A_SUBJECT, A_NAME);
        assertThat(deliveryOptions.emailAddress).isEqualTo(AN_EMAIL_ADDRESS);
    }
}