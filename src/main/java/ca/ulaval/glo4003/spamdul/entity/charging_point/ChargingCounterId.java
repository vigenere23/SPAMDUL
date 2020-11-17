package ca.ulaval.glo4003.spamdul.entity.charging_point;

import ca.ulaval.glo4003.spamdul.entity.idgenerator.IdGenerator;
import ca.ulaval.glo4003.spamdul.entity.idgenerator.IncrementalLongIdGenerator;
import java.util.Objects;

public class ChargingCounterId {

  private static final IdGenerator<Long> idGenerator = new IncrementalLongIdGenerator();
  private final Long value;

  public ChargingCounterId() {
    value = idGenerator.getNextId();
  }

  private ChargingCounterId(long value) {
    this.value = value;
  }

  public static ChargingCounterId valueOf(String parkingAccessLogId) {
    return new ChargingCounterId(Long.parseLong(parkingAccessLogId));
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChargingCounterId id = (ChargingCounterId) o;

    return Objects.equals(this.value, id.value);
  }

  @Override public int hashCode() {
    return Objects.hash(value);
  }

  @Override public String toString() {
    return this.value.toString();
  }
}
