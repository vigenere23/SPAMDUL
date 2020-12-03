package ca.ulaval.glo4003.spamdul.entity.user.car;

import ca.ulaval.glo4003.spamdul.entity.idgenerator.IdGenerator;
import ca.ulaval.glo4003.spamdul.entity.idgenerator.IncrementalLongIdGenerator;
import java.util.Objects;

public class CarId {

  private static final IdGenerator<Long> idGenerator = new IncrementalLongIdGenerator();
  private final Long id;

  public CarId() {
    id = idGenerator.getNextId();
  }

  private CarId(long id) {
    this.id = id;
  }

  public static CarId valueOf(String userId) {
    return new CarId(Long.parseLong(userId));
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CarId carId = (CarId) o;

    return Objects.equals(id, carId.id);
  }

  @Override public int hashCode() {
    return Objects.hash(id);
  }

  @Override public String toString() {
    return this.id.toString();
  }
}
