package ca.ulaval.glo4003.spamdul.entity.delivery;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.delivery.email.EmailServiceProvider;
import ca.ulaval.glo4003.spamdul.infrastructure.delivery.email.NullEmailService;
import ca.ulaval.glo4003.spamdul.entity.delivery.sspoffice.LoggerSSPOfficeService;
import ca.ulaval.glo4003.spamdul.infrastructure.delivery.post.LoggerPostalService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeliveryStrategyFactoryTest {

  private DeliveryStrategyFactory deliveryStrategyFactory;

  @Mock
  private EmailServiceProvider emailServiceProvider;
  @Mock
  private NullEmailService emailService;

  @Before
  public void setUp() {
    deliveryStrategyFactory = new DeliveryStrategyFactory(emailServiceProvider);
    when(emailServiceProvider.provide()).thenReturn(emailService);
  }

  @Test
  public void givenEmailDeliveryMode_whenCreatingStrategy_shouldReturnFromEmailServiceProvider() {
    DeliveryStrategy deliveryStrategy = deliveryStrategyFactory.create(DeliveryMode.EMAIL);

    assertThat(deliveryStrategy).isEqualTo(emailService);
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
