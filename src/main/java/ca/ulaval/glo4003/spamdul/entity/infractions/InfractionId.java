package ca.ulaval.glo4003.spamdul.entity.infractions;

import ca.ulaval.glo4003.spamdul.entity.idgenerator.IdGenerator;
import ca.ulaval.glo4003.spamdul.entity.idgenerator.IncrementalLongIdGenerator;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InvalidInfractionIdException;
import java.util.Objects;

public class InfractionId {

  private static final IdGenerator<Long> idGenerator = new IncrementalLongIdGenerator();
  private final Long id;

  public InfractionId() {
    id = idGenerator.getNextId();
  }

  private InfractionId(long id) {
    this.id = id;
  }

  public static InfractionId valueOf(String id) {
    try {
      return new InfractionId(Long.parseLong(id));

    } catch (NumberFormatException e) {
      throw new InvalidInfractionIdException();
    }
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InfractionId infractionId = (InfractionId) o;

    return Objects.equals(id, infractionId.id);
  }

  @Override public int hashCode() {
    return Objects.hash(id);
  }

  @Override public String toString() {
    return this.id.toString();
  }
}
