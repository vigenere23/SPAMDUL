package ca.ulaval.glo4003.spamdul.entity.infractions;

import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InvalidInfractionIdException;
import java.util.Objects;

public class InfractionId {

  private static Long lastCode = 0L;
  private final Long id;

  public InfractionId() {
    id = getNextCode();
  }

  private InfractionId(long id) {
    this.id = id;
  }

  public static InfractionId valueOf(String id) {
    try {
      return new InfractionId(Long.parseLong(id));

    } catch (NumberFormatException e) {
      throw new InvalidInfractionIdException("Invalid infraction id format");
    }
  }

  private static synchronized Long getNextCode() {
    lastCode += 1;

    return lastCode;
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InfractionId infractionId = (InfractionId) o;

    return Objects.equals(id, infractionId.id);
  }

  public int hashCode() {
    return Objects.hash(id);
  }

  public String toString() {
    return this.id.toString();
  }
}