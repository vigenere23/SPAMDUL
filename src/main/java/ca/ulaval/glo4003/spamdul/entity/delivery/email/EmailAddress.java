package ca.ulaval.glo4003.spamdul.entity.delivery.email;

import java.util.Objects;

public class EmailAddress {

  private String address;

  public EmailAddress(String address) {
    this.address = address;
  }

  public String toString() {
    return address;
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EmailAddress emailAddress = (EmailAddress) o;

    return toString().equals(emailAddress.toString());
  }

  public int hashCode() {
    return Objects.hash(toString());
  }
}
