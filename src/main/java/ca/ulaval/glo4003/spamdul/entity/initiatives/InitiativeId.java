package ca.ulaval.glo4003.spamdul.entity.initiatives;

import ca.ulaval.glo4003.spamdul.entity.idgenerator.IdGenerator;
import ca.ulaval.glo4003.spamdul.entity.idgenerator.IncrementalLongIdGenerator;
import java.util.Objects;

public class InitiativeId {

  private static final IdGenerator<Long> idGenerator = new IncrementalLongIdGenerator();
  private final Long id;

  public InitiativeId() {
    id = idGenerator.getNextId();
  }

  private InitiativeId(long id) {
    this.id = id;
  }

  public static InitiativeId valueOf(String initiativeId) {
    return new InitiativeId(Long.parseLong(initiativeId));
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    InitiativeId other = (InitiativeId) object;

    return Objects.equals(id, other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return this.id.toString();
  }
}
