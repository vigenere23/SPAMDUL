package ca.ulaval.glo4003.spamdul.entity.delivery;

import ca.ulaval.glo4003.spamdul.infrastructure.delivery.email.GmailEmailService;
import ca.ulaval.glo4003.spamdul.infrastructure.delivery.post.LoggerPostalService;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class DeliveryStrategyFactoryTest {
    private DeliveryStrategyFactory deliveryStrategyFactory;

    @Before
    public void setUp() {
        deliveryStrategyFactory = new DeliveryStrategyFactory();
    }

    @Test
    public void givenEmailDeliveryMode_whenCreatingStrategy_shouldReturnGmailEmailService() {
        DeliveryStrategy deliveryStrategy = deliveryStrategyFactory.create(DeliveryMode.EMAIL);

        assertThat(deliveryStrategy instanceof GmailEmailService).isTrue();
    }

    @Test
    public void givenPostDeliveryMode_whenCreatingStrategy_shouldReturnLoggerPostalService() {
        DeliveryStrategy deliveryStrategy = deliveryStrategyFactory.create(DeliveryMode.POST);

        assertThat(deliveryStrategy instanceof LoggerPostalService).isTrue();

    }
}
