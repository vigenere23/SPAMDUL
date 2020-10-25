package ca.ulaval.glo4003.spamdul.entity.car;

import java.util.Objects;

public class CarId {

  private static Long lastId = 0L;
  private final Long id;

  public CarId() {
    id = getNextId();
  }

  private CarId(long id) {
    this.id = id;
  }

  public static CarId valueOf(String userId) {
    return new CarId(Long.parseLong(userId));
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
    CarId carId = (CarId) o;

    return Objects.equals(id, carId.id);
  }

  public int hashCode() {
    return Objects.hash(id);
  }

  public String toString() {
    return this.id.toString();
  }

}
