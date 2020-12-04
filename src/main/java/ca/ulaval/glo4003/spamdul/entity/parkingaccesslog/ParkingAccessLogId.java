package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.entity.ids.IdGenerator;
import ca.ulaval.glo4003.spamdul.entity.ids.IncrementalLongIdGenerator;
import java.util.Objects;

public class ParkingAccessLogId {

  private static final IdGenerator<Long> idGenerator = new IncrementalLongIdGenerator();
  private final Long value;

  public ParkingAccessLogId() {
    value = idGenerator.getNextId();
  }

  private ParkingAccessLogId(long value) {
    this.value = value;
  }

  public static ParkingAccessLogId valueOf(String parkingAccessLogId) {
    return new ParkingAccessLogId(Long.parseLong(parkingAccessLogId));
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ParkingAccessLogId id = (ParkingAccessLogId) o;

    return Objects.equals(value, id.value);
  }

  @Override public int hashCode() {
    return Objects.hash(value);
  }

  @Override public String toString() {
    return this.value.toString();
  }
}
