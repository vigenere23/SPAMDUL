package ca.ulaval.glo4003.spamdul.entity.delivery.post;

import ca.ulaval.glo4003.spamdul.entity.car.CarId;

import java.util.Objects;

// TODO: add to user
public class PostalAddress {

  private String address;


  public PostalAddress(String address) {
    this.address = address;
  }

  public String getAddress() {
    return address;
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostalAddress postalAddress = (PostalAddress) o;
    return address.equals(postalAddress.address);
  }

  public int hashCode() {
    return Objects.hash(address);
  }
}
