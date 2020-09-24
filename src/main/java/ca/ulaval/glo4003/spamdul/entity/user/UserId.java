package ca.ulaval.glo4003.spamdul.entity.user;

import java.util.Objects;

public class UserId {

  private static Long lastId = 0L;
  private final Long id;

  public UserId() {
    id = getNextId();
  }

  //TODO pas encore utilise mais le sera bientot pour retrouver un user quand il le faudra

  private UserId(long id) {
    this.id = id;
  }

  public static UserId valueOf(String userId) {
    return new UserId(Long.parseLong(userId));
  }

  private static synchronized Long getNextId() {
    lastId += 1;

    return lastId;
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
