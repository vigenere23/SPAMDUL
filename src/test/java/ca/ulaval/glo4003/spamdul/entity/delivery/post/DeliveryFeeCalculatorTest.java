package ca.ulaval.glo4003.spamdul.entity.delivery.post;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryMode;
import ca.ulaval.glo4003.spamdul.shared.amount.Amount;
import org.junit.Before;
import org.junit.Test;

public class DeliveryFeeCalculatorTest {

  private static final DeliveryMode A_POST_DELIVERY_MODE = DeliveryMode.POST;
  private static final DeliveryMode A_SSP_DELIVERY_MODE = DeliveryMode.SSP_OFFICE;
  private static final DeliveryMode A_EMAIL_DELIVERY_MODE = DeliveryMode.EMAIL;
  private static final Amount POST_FEE = Amount.valueOf(5.0);
  private static final Amount SSP_FEE = Amount.valueOf(0.0);
  private static final Amount EMAIL_FEE = Amount.valueOf(0.0);

  private DeliveryFeeCalculator deliveryFeeCalculator;

  @Before
  public void setUp() {
    deliveryFeeCalculator = new DeliveryFeeCalculator();
  }

  @Test
  public void givenAPostDeliveryMode_whenFindingDeliveryFee_thenShouldReturnRightFee() {
    assertThat(deliveryFeeCalculator.calculateBy(A_POST_DELIVERY_MODE)).isEqualTo(POST_FEE);
  }

  @Test
  public void givenASspDeliveryMode_whenFindingDeliveryFee_thenShouldReturnRightFee() {
    assertThat(deliveryFeeCalculator.calculateBy(A_SSP_DELIVERY_MODE)).isEqualTo(SSP_FEE);
  }

  @Test
  public void givenAEmailDeliveryMode_whenFindingDeliveryFee_thenShouldReturnRightFee() {
    assertThat(deliveryFeeCalculator.calculateBy(A_EMAIL_DELIVERY_MODE)).isEqualTo(EMAIL_FEE);
  }
}

