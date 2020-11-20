package ca.ulaval.glo4003.spamdul.entity.charging_point;

import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.InvalidChargingPointIdException;
import ca.ulaval.glo4003.spamdul.entity.idgenerator.IdGenerator;
import ca.ulaval.glo4003.spamdul.entity.idgenerator.IncrementalLongIdGenerator;
import java.util.Objects;

public class ChargingPointId {

  private static final IdGenerator<Long> idGenerator = new IncrementalLongIdGenerator();
  private final Long value;

  public ChargingPointId() {
    value = idGenerator.getNextId();
  }

  private ChargingPointId(long value) {
    this.value = value;
  }

  public static ChargingPointId valueOf(String parkingAccessLogId) {
    try {
      return new ChargingPointId(Long.parseLong(parkingAccessLogId));
    } catch (Exception e) {
      throw new InvalidChargingPointIdException();
    }
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChargingPointId id = (ChargingPointId) o;

    return Objects.equals(this.value, id.value);
  }

  @Override public int hashCode() {
    return Objects.hash(value);
  }

  @Override public String toString() {
    return this.value.toString();
  }
}