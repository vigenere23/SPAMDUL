package ca.ulaval.glo4003.spamdul.infrastructure.db.delivery;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.delivery.post.PostFee;
import ca.ulaval.glo4003.spamdul.entity.delivery.post.PostFeeRepository;
import org.junit.Before;
import org.junit.Test;

public class HardcodedPostFeeRepositoryTest {

  PostFee POST_FEE = new PostFee(5.0);

  PostFeeRepository postFeeRepository;

  @Before
  public void setUp() {
    postFeeRepository = new HardcodedPostFeeRepository();
  }

  @Test
  public void whenFindingPostFee_thenShouldReturnRightFee() {
    assertThat(postFeeRepository.find().getFee()).isEqualTo(POST_FEE.getFee());
  }

}
