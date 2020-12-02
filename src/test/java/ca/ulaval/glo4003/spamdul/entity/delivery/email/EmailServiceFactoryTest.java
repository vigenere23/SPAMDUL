package ca.ulaval.glo4003.spamdul.entity.delivery.email;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceFactoryTest {

  @Mock
  private DeliveryStrategy emailService;

  @Test
  public void givenEmailService_whenCreating_returnsGivenEmailService() {
    EmailServiceFactory emailServiceFactory = new EmailServiceFactory(emailService);

    DeliveryStrategy deliveryStrategy = emailServiceFactory.create();

    assertThat(deliveryStrategy).isEqualTo(emailService);
  }
}
