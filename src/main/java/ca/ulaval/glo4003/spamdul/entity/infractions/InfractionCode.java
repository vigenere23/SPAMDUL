package ca.ulaval.glo4003.spamdul.entity.infractions;

import java.util.Objects;

public class InfractionCode {

  private String codeString;

  private InfractionCode(String codeString) {
    this.codeString = codeString;
  }

  public static InfractionCode valueOf(String codeString) {
    return new InfractionCode(codeString);
  }

  public String toString() {
    return codeString;
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InfractionCode that = (InfractionCode) o;

    return Objects.equals(codeString, that.codeString);
  }

  public int hashCode() {
    return Objects.hash(codeString);
  }
}
