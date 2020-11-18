package ca.ulaval.glo4003.spamdul.entity.delivery;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.delivery.sspoffice.LoggerSSPOfficeService;
import ca.ulaval.glo4003.spamdul.infrastructure.delivery.email.GmailEmailService;
import ca.ulaval.glo4003.spamdul.infrastructure.delivery.post.LoggerPostalService;
import org.junit.Before;
import org.junit.Test;

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

  @Test
  public void givenSSPOfficeDeliveryMode_whenCreatingStrategy_shouldReturnLoggerSSPOfficeService() {
    DeliveryStrategy deliveryStrategy = deliveryStrategyFactory.create(DeliveryMode.SSP_OFFICE);

    assertThat(deliveryStrategy instanceof LoggerSSPOfficeService).isTrue();
  }
}
