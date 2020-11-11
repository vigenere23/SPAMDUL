package ca.ulaval.glo4003.spamdul.entity.delivery;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.delivery.post.PostFee;
import ca.ulaval.glo4003.spamdul.entity.delivery.post.DeliveryFeeCalculator;
import org.junit.Before;
import org.junit.Test;

public class DeliveryFeeCalculatorTest {
  private static final DeliveryMode A_POST_DELIVERY_MODE = DeliveryMode.POST;
  private static final DeliveryMode A_SSP_DELIVERY_MODE = DeliveryMode.SSP_OFFICE;
  private static final DeliveryMode A_EMAIL_DELIVERY_MODE = DeliveryMode.EMAIL;
  private static final PostFee POST_FEE = new PostFee(5.0);
  private static final PostFee SSP_FEE = new PostFee(0.0);
  private static final PostFee EMAIL_FEE = new PostFee(0.0);

  private DeliveryFeeCalculator deliveryFeeCalculator;

  @Before
  public void setUp() {
    deliveryFeeCalculator = new DeliveryFeeCalculator();
  }

  @Test
  public void givenAPostDeliveryMode_whenFindingDeliveryFee_thenShouldReturnRightFee() {
    assertThat(deliveryFeeCalculator.calculateBy(A_POST_DELIVERY_MODE).getFee()).isEqualTo(POST_FEE.getFee());
  }

  @Test
  public void givenASspDeliveryMode_whenFindingDeliveryFee_thenShouldReturnRightFee() {
    assertThat(deliveryFeeCalculator.calculateBy(A_SSP_DELIVERY_MODE).getFee()).isEqualTo(SSP_FEE.getFee());
  }

  @Test
  public void givenAEmailDeliveryMode_whenFindingDeliveryFee_thenShouldReturnRightFee() {
    assertThat(deliveryFeeCalculator.calculateBy(A_EMAIL_DELIVERY_MODE).getFee()).isEqualTo(EMAIL_FEE.getFee());
  }
}

