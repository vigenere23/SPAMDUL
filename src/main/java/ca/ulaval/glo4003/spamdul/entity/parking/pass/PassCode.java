package ca.ulaval.glo4003.spamdul.entity.parking.pass;

import ca.ulaval.glo4003.spamdul.entity.idgenerator.IdGenerator;
import ca.ulaval.glo4003.spamdul.entity.idgenerator.IncrementalLongIdGenerator;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.exceptions.InvalidPassCodeFormatException;
import java.util.Objects;

public class PassCode {

  private static final IdGenerator<Long> idGenerator = new IncrementalLongIdGenerator();
  private final Long code;

  public PassCode() {
    code = idGenerator.getNextId();
  }

  private PassCode(long code) {
    this.code = code;
  }

  public static PassCode valueOf(String passCode) {
    try {
      return new PassCode(Long.parseLong(passCode));

    } catch (NumberFormatException e) {
      throw new InvalidPassCodeFormatException();
    }
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PassCode userId = (PassCode) o;

    return Objects.equals(code, userId.code);
  }

  @Override public int hashCode() {
    return Objects.hash(code);
  }

  @Override public String toString() {
    return this.code.toString();
  }
}
