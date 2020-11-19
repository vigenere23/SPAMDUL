package ca.ulaval.glo4003.spamdul.entity.delivery.email;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryStrategy;
import ca.ulaval.glo4003.spamdul.infrastructure.delivery.email.GmailEmailService;
import ca.ulaval.glo4003.spamdul.infrastructure.delivery.email.NullEmailService;
import org.junit.Test;

public class EmailServiceProviderTest {

  @Test
  public void givenENVModeToCi_whenProviding_returnsNullEmailService() throws Exception {
    EmailServiceProvider emailServiceProvider = new EmailServiceProvider("ci");

    DeliveryStrategy deliveryStrategy = emailServiceProvider.provide();

    assertThat(deliveryStrategy instanceof NullEmailService).isTrue();
  }

  @Test
  public void givenENVModeToAnythingButCi_whenProviding_returnsGmailEmailService() throws Exception {
    EmailServiceProvider emailServiceProvider = new EmailServiceProvider(null);

    DeliveryStrategy deliveryStrategy = emailServiceProvider.provide();

    assertThat(deliveryStrategy instanceof GmailEmailService).isTrue();
  }
}
