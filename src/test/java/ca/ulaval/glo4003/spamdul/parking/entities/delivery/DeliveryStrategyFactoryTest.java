package ca.ulaval.glo4003.spamdul.parking.entities.delivery;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.parking.entities.delivery.email.EmailDeliverer;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.post.PostalDeliverer;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.sspoffice.SSPOfficeDeliverer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeliveryStrategyFactoryTest {

  private DeliveryStrategyFactory deliveryStrategyFactory;

  @Mock
  private EmailDeliverer emailService;
  @Mock
  private PostalDeliverer postalService;
  @Mock
  private SSPOfficeDeliverer sspService;

  @Before
  public void setUp() {
    deliveryStrategyFactory = new DeliveryStrategyFactory(emailService, postalService, sspService);
  }

  @Test
  public void givenEmailDeliveryMode_whenCreatingStrategy_shouldReturnEmailService() {
    DeliveryStrategy deliveryStrategy = deliveryStrategyFactory.create(DeliveryMode.EMAIL);

    assertThat(deliveryStrategy).isEqualTo(emailService);
  }

  @Test
  public void givenPostDeliveryMode_whenCreatingStrategy_shouldReturnPostalService() {
    DeliveryStrategy deliveryStrategy = deliveryStrategyFactory.create(DeliveryMode.POST);

    assertThat(deliveryStrategy).isEqualTo(postalService);
  }

  @Test
  public void givenSSPOfficeDeliveryMode_whenCreatingStrategy_shouldReturnSSPOfficeService() {
    DeliveryStrategy deliveryStrategy = deliveryStrategyFactory.create(DeliveryMode.SSP_OFFICE);

    assertThat(deliveryStrategy).isEqualTo(sspService);
  }
}
