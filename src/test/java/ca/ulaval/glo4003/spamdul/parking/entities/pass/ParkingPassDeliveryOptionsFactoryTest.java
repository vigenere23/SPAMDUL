package ca.ulaval.glo4003.spamdul.parking.entities.pass;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.parking.entities.delivery.DeliveryMode;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.DeliveryOptions;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.InvalidDeliveryModeException;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.email.EmailAddress;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.post.PostalAddress;
import ca.ulaval.glo4003.spamdul.parking.usecases.pass.DeliveryDto;
import org.junit.Before;
import org.junit.Test;

public class ParkingPassDeliveryOptionsFactoryTest {

  private static final String A_SUBJECT = "djehfj";
  private static final String AN_EMAIL_ADDRESS = "fdjk";

  private PostalAddress postalAddress;
  private EmailAddress emailAddress;
  private DeliveryDto deliveryDto;

  private ParkingPassDeliveryOptionsFactory parkingPassDeliveryOptionsFactory;

  @Before
  public void setUpDto() {
    parkingPassDeliveryOptionsFactory = new ParkingPassDeliveryOptionsFactory();
    deliveryDto = new DeliveryDto();
    emailAddress = new EmailAddress(AN_EMAIL_ADDRESS);
    postalAddress = new PostalAddress("", "", "", "", "", "", "");
    deliveryDto.emailAddress = emailAddress;
    deliveryDto.postalAddress = postalAddress;

  }

  @Test(expected = InvalidDeliveryModeException.class)
  public void givenInvalidDeliveryStrategy_whenCreatingDeliveryOptions_shouldThrowInvalidDeliveryStrategyException() {
    parkingPassDeliveryOptionsFactory.create(deliveryDto, A_SUBJECT);
  }

  @Test
  public void givenPostalDelivery_whenCreatingDeliveryOptions_shouldCopyPostalAddress() {
    deliveryDto.deliveryMode = DeliveryMode.POST;

    DeliveryOptions deliveryOptions = parkingPassDeliveryOptionsFactory.create(deliveryDto, A_SUBJECT);

    assertThat(deliveryOptions.subject).isEqualTo(A_SUBJECT);
    assertThat(deliveryOptions.postalAddress).isEqualTo(postalAddress);
    assertThat(deliveryOptions.emailAddress).isNull();
  }

  @Test
  public void givenEmailDelivery_whenCreatingDeliveryOptions_shouldCopyPostalAddress() {
    deliveryDto.deliveryMode = DeliveryMode.EMAIL;

    DeliveryOptions deliveryOptions = parkingPassDeliveryOptionsFactory.create(deliveryDto, A_SUBJECT);

    assertThat(deliveryOptions.subject).isEqualTo(A_SUBJECT);
    assertThat(deliveryOptions.emailAddress).isEqualTo(emailAddress);
    assertThat(deliveryOptions.postalAddress).isNull();
  }

  @Test
  public void givenSSPOfficeDeliveryMode_whenCreatingDeliveryOptions_shouldOnlySetSubject() {
    deliveryDto.deliveryMode = DeliveryMode.SSP_OFFICE;

    DeliveryOptions deliveryOptions = parkingPassDeliveryOptionsFactory.create(deliveryDto, A_SUBJECT);

    assertThat(deliveryOptions.subject).isEqualTo(A_SUBJECT);
    assertThat(deliveryOptions.emailAddress).isNull();
    assertThat(deliveryOptions.postalAddress).isNull();
  }
}
