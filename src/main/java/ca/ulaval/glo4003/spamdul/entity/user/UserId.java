package ca.ulaval.glo4003.spamdul.entity.user;

import ca.ulaval.glo4003.spamdul.entity.ids.Id;

public class UserId extends Id {

  private UserId(String value) {
    super(value);
  }

  public static UserId valueOf(String userId) {
    return new UserId(userId);
  }
}
