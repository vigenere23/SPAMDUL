package ca.ulaval.glo4003.spamdul.assemblers.delivery;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.parking.entities.delivery.email.EmailAddress;
import ca.ulaval.glo4003.spamdul.assemblers.delivery.exceptions.InvalidEmailAddressException;
import org.junit.Test;

public class EmailAddressAssemblerTest {

  private static final String VALID_EMAIL = "lewkjj@hotmail.com";

  private EmailAddressAssembler emailAddressAssembler = new EmailAddressAssembler();

  @Test
  public void whenAssemblingValidAddress_shouldReturnEmailAddress() {
    EmailAddress emailAddress = emailAddressAssembler.fromString(VALID_EMAIL);

    assertThat(emailAddress.toString()).isEqualTo(VALID_EMAIL);
  }

  @Test(expected = InvalidEmailAddressException.class)
  public void whenAssemblingInvalidAddressCase1_shouldThrowInvalidAddressException() {
    EmailAddress emailAddress = emailAddressAssembler.fromString("");
  }

  @Test(expected = InvalidEmailAddressException.class)
  public void whenAssemblingInvalidAddressCase2_shouldThrowInvalidAddressException() {
    EmailAddress emailAddress = emailAddressAssembler.fromString("djdj@dkjfk");
  }

  @Test(expected = InvalidEmailAddressException.class)
  public void whenAssemblingOtherInvalidAddressCase3_shouldThrowInvalidAddressException() {
    EmailAddress emailAddress = emailAddressAssembler.fromString("djdjdkjfk");
  }

  @Test(expected = InvalidEmailAddressException.class)
  public void whenAssemblingOtherInvalidAddressCase4_shouldThrowInvalidAddressException() {
    EmailAddress emailAddress = emailAddressAssembler.fromString("djdjdkj@fk@.com");
  }
}
