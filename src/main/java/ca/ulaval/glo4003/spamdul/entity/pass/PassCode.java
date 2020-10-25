package ca.ulaval.glo4003.spamdul.entity.pass;

import ca.ulaval.glo4003.spamdul.entity.pass.exceptions.InvalidPassCodeFormat;
import java.util.Objects;

public class PassCode {

  private static Long lastCode = 0L;
  private final Long code;

  public PassCode() {
    code = getNextCode();
  }

  private PassCode(long code) {
    this.code = code;
  }

  public static PassCode valueOf(String passCode) {
    try {
      return new PassCode(Long.parseLong(passCode));
    } catch (NumberFormatException e) {
      throw new InvalidPassCodeFormat("Invalid pass code format");
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
    PassCode userId = (PassCode) o;
    return Objects.equals(code, userId.code);
  }

  public int hashCode() {
    return Objects.hash(code);
  }

  public String toString() {
    return this.code.toString();
  }
}
