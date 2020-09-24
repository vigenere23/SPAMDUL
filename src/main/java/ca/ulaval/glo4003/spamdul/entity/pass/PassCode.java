package ca.ulaval.glo4003.spamdul.entity.pass;

import java.util.Objects;

public class PassCode {

  private static Long lastCode = 0L;
  private final Long code;

  public PassCode() {
    code = getNextCode();
  }

  //TODO pas encore utilise mais le sera bientot pour retrouver un user quand il le faudra

  private PassCode(long code) {
    this.code = code;
  }

  public static PassCode valueOf(String userId) {
    return new PassCode(Long.parseLong(userId));
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
