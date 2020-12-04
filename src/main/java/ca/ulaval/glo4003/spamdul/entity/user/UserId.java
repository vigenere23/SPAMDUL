package ca.ulaval.glo4003.spamdul.entity.user;

import ca.ulaval.glo4003.spamdul.entity.ids.IdGenerator;
import ca.ulaval.glo4003.spamdul.entity.ids.IncrementalLongIdGenerator;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.InvalidUserIdFormatException;
import java.util.Objects;

public class UserId {

  private static final IdGenerator<Long> idGenerator = new IncrementalLongIdGenerator();
  private final Long id;

  public UserId() {
    id = idGenerator.getNextId();
  }

  private UserId(long id) {
    this.id = id;
  }

  public static UserId valueOf(String userId) {
    try {
      return new UserId(Long.parseLong(userId));
    } catch (NumberFormatException e) {
      throw new InvalidUserIdFormatException();
    }
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserId userId = (UserId) o;

    return Objects.equals(id, userId.id);
  }

  @Override public int hashCode() {
    return Objects.hash(id);
  }

  @Override public String toString() {
    return this.id.toString();
  }
}
