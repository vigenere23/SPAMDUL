package ca.ulaval.glo4003.spamdul.infrastructure.db.delivery;

import ca.ulaval.glo4003.spamdul.entity.delivery.post.PostFee;
import ca.ulaval.glo4003.spamdul.entity.delivery.post.PostFeeRepository;

public class HardcodedPostFeeRepository implements PostFeeRepository {
  PostFee fee = new PostFee(5.0);

  public PostFee find() {
    return fee;
  }
}
