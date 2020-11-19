package ca.ulaval.glo4003.spamdul.entity.rechargul;

import ca.ulaval.glo4003.spamdul.entity.idgenerator.IdGenerator;
import ca.ulaval.glo4003.spamdul.entity.idgenerator.IncrementalLongIdGenerator;
import java.util.Objects;

public class RechargULCardId {

  private static final IdGenerator<Long> idGenerator = new IncrementalLongIdGenerator();
  private final Long value;

  public RechargULCardId() {
    value = idGenerator.getNextId();
  }

  private RechargULCardId(long value) {
    this.value = value;
  }

  private RechargULCardId(String value) {
    this.value = Long.parseLong(value);
  }

  public static RechargULCardId valueOf(String parkingAccessLogId) {
    return new RechargULCardId(Long.parseLong(parkingAccessLogId));
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RechargULCardId id = (RechargULCardId) o;

    return Objects.equals(this.value, id.value);
  }

  @Override public int hashCode() {
    return Objects.hash(value);
  }

  @Override public String toString() {
    return this.value.toString();
  }
}
