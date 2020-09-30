package ca.ulaval.glo4003.spamdul.entity.delivery;

import ca.ulaval.glo4003.spamdul.infrastructure.delivery.email.GmailEmailService;
import ca.ulaval.glo4003.spamdul.infrastructure.delivery.post.LoggerPostalService;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class DeliveryBridgeFactoryTest {
    private DeliveryBridgeFactory deliveryBridgeFactory;

    @Before
    public void setUp() {
        deliveryBridgeFactory = new DeliveryBridgeFactory();
    }

    @Test
    public void givenEmailDeliveryMode_whenCreatingBridge_shouldReturnGmailEmailService() {
        DeliveryBridge deliveryBridge = deliveryBridgeFactory.create(DeliveryMode.EMAIL);

        assertThat(deliveryBridge instanceof GmailEmailService).isTrue();
    }

    @Test
    public void givenPostDeliveryMode_whenCreatingBridge_shouldReturnLoggerPostalService() {
        DeliveryBridge deliveryBridge = deliveryBridgeFactory.create(DeliveryMode.POST);

        assertThat(deliveryBridge instanceof LoggerPostalService).isTrue();

    }
}
