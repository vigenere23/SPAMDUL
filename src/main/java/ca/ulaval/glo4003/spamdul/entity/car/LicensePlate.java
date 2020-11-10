package ca.ulaval.glo4003.spamdul.entity.car;

import java.util.Objects;

public class LicensePlate {

  private String licensePlate;

  public LicensePlate(String licensePlate) {
    this.licensePlate = licensePlate;
  }

  public String toString() {
    return licensePlate;
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LicensePlate that = (LicensePlate) o;
    return Objects.equals(licensePlate, that.licensePlate);
  }

  public int hashCode() {
    return Objects.hash(licensePlate);
  }
}
