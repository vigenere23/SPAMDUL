package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import java.util.Objects;

public class ParkingAccessLogId {

  private static Long lastId = 0L;
  private final Long id;

  public ParkingAccessLogId() {
    id = getNextId();
  }

  //TODO pas encore utilise mais le sera bientot pour retrouver un user quand il le faudra

  private ParkingAccessLogId(long id) {
    this.id = id;
  }

  public static ParkingAccessLogId valueOf(String parkingAccessLogId) {
    return new ParkingAccessLogId(Long.parseLong(parkingAccessLogId));
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
    ParkingAccessLogId userId = (ParkingAccessLogId) o;
    return Objects.equals(id, userId.id);
  }

  public int hashCode() {
    return Objects.hash(id);
  }

  public String toString() {
    return this.id.toString();
  }
}
