package ca.ulaval.glo4003.spamdul.parking.entities.infractions;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.parking.entities.infractions.exceptions.AlreadyPaidInfractionException;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import org.junit.Test;

public class InfractionTest {

  public static final String ANY_MESSAGE = "test";
  public static final Amount ANY_AMOUNT = Amount.valueOf(598.65);
  public static final InfractionId AN_INFRACTION_ID = InfractionId.valueOf("123");

  public final String AN_INFRACTION_CODE_VALUE = "00";
  public final InfractionCode AN_INFRACTION_CODE = InfractionCode.valueOf(AN_INFRACTION_CODE_VALUE);

  Infraction infraction = new Infraction(AN_INFRACTION_ID, ANY_MESSAGE, AN_INFRACTION_CODE, ANY_AMOUNT);

  @Test
  public void givenUnpaid_whenPay_shouldBePaid() {
    infraction.pay();

    assertThat(infraction.isPaid()).isTrue();
  }

  @Test(expected = AlreadyPaidInfractionException.class)
  public void givenPaid_whenPay_shouldThrow() {
    infraction.pay();

    infraction.pay();
  }
}
