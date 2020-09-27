package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.entity.idgenerator.IdGenerator;
import ca.ulaval.glo4003.spamdul.entity.idgenerator.IncrementalLongIdGenerator;
import java.util.Objects;

public class ParkingAccessLogId {

  private static final IdGenerator<Long> idGenerator = new IncrementalLongIdGenerator();
  private final Long id;

  public ParkingAccessLogId() {
    id = idGenerator.getNextId();
  }

  private ParkingAccessLogId(long id) {
    this.id = id;
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
    ParkingAccessLogId userId = (ParkingAccessLogId) o;
    return Objects.equals(id, userId.id);
  }

  @Override public int hashCode() {
    return Objects.hash(id);
  }

  @Override public String toString() {
    return this.id.toString();
  }
}
