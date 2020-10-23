package ca.ulaval.glo4003.spamdul.entity.delivery;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.delivery.post.PostalAddress;
import org.junit.Test;

public class PostalAddressTest {

  @Test
  public void whenComparingDifferentPostalAddress_shouldNotBeEqual() {
    PostalAddress postalAddress = new PostalAddress("allo", "allo", "allo", "allo", "allo", "allo", "allo");
    PostalAddress anotherPostalAddress = new PostalAddress("non", "non", "non", "non", "non", "non", "non");

    assertThat(postalAddress).isNotEqualTo(anotherPostalAddress);
    assertThat(postalAddress.hashCode()).isNotEqualTo(anotherPostalAddress.hashCode());
  }

  @Test
  public void whenComparingTheSamePostalAddress_shouldBeEqual() {
    PostalAddress postalAddress = new PostalAddress("allo", "allo", "allo", "allo", "allo", "allo", "allo");
    PostalAddress anotherPostalAddress = new PostalAddress("allo", "allo", "allo", "allo", "allo", "allo", "allo");

    assertThat(postalAddress).isEqualTo(anotherPostalAddress);
    assertThat(postalAddress.hashCode()).isEqualTo(anotherPostalAddress.hashCode());
  }

  @Test
  public void givenNullLine2_whenOutputtingToString_formatShouldBeRight() {
    String expectedOutput = ("allo \nallo \nallo \nallo \nallo \nallo \n");
    PostalAddress postalAddress = new PostalAddress("allo", "allo", null, "allo", "allo", "allo", "allo");

    assertThat(postalAddress.toString()).isEqualTo(expectedOutput);
  }

  @Test
  public void givenNotNullLine2_whenOutputtingToString_formatShouldBeRight() {
    String expectedOutput = ("allo \nallo \nallo \nallo \nallo \nallo \nallo \n");
    PostalAddress postalAddress = new PostalAddress("allo", "allo", "allo", "allo", "allo", "allo", "allo");

    assertThat(postalAddress.toString()).isEqualTo(expectedOutput);
  }
}
