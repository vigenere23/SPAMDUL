package ca.ulaval.glo4003.spamdul.parking.entities.delivery.email;

import java.util.Objects;

public class EmailAddress {

  private final String address;

  public EmailAddress(String address) {
    this.address = address;
  }

  @Override public String toString() {
    return address;
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EmailAddress emailAddress = (EmailAddress) o;

    return toString().equals(emailAddress.toString());
  }

  @Override public int hashCode() {
    return Objects.hash(toString());
  }
}
