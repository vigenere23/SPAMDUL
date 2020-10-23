package ca.ulaval.glo4003.spamdul.entity.user;

import ca.ulaval.glo4003.spamdul.entity.idgenerator.IdGenerator;
import ca.ulaval.glo4003.spamdul.entity.idgenerator.IncrementalLongIdGenerator;
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
    return new UserId(Long.parseLong(userId));
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserId userId = (UserId) o;
    return Objects.equals(id, userId.id);
  }

  public int hashCode() {
    return Objects.hash(id);
  }

  public String toString() {
    return this.id.toString();
  }
}
