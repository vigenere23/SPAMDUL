package ca.ulaval.glo4003.spamdul.entity.user;

import ca.ulaval.glo4003.spamdul.entity.ids.LongId;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.InvalidUserIdFormatException;

public class UserId extends LongId {

  private UserId(long value) {
    super(value);
  }

  public static UserId valueOf(String userId) {
    try {
      return new UserId(Long.parseLong(userId));
    } catch (NumberFormatException e) {
      throw new InvalidUserIdFormatException();
    }
  }
}
