package ca.ulaval.glo4003.spamdul.parking.entities.delivery.email;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class EmailAddressTest {

  public static final String A_EMAIL_ADDRESS = "allo";

  @Test
  public void whenComparingDifferentEmailAddress_shouldNotBeEqual() {
    EmailAddress emailAddress = new EmailAddress("allo");
    EmailAddress anotherEmailAddress = new EmailAddress("non");

    assertThat(emailAddress).isNotEqualTo(anotherEmailAddress);
    assertThat(emailAddress.hashCode()).isNotEqualTo(anotherEmailAddress.hashCode());
  }

  @Test
  public void whenComparingTheSameEmailAddress_shouldBeEqual() {
    EmailAddress emailAddress = new EmailAddress(A_EMAIL_ADDRESS);
    EmailAddress sameEmailAddress = new EmailAddress(A_EMAIL_ADDRESS);

    assertThat(emailAddress).isEqualTo(sameEmailAddress);
    assertThat(emailAddress.hashCode()).isEqualTo(sameEmailAddress.hashCode());
  }
}
