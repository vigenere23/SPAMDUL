package ca.ulaval.glo4003.spamdul.entity.delivery.post;

import java.util.Objects;

public class PostalAddress {

  private String name;
  private String line1;
  private String line2;
  private String city;
  private String province;
  private String postalCode;
  private String country;

  public PostalAddress(String name, String line1, String line2, String city, String province, String postalCode,
                       String country) {
    this.name = name;
    this.line1 = line1;
    this.line2 = line2;
    this.city = city;
    this.province = province;
    this.postalCode = postalCode;
    this.country = country;
  }

  public String toString() {
    if (line2 == null) {
      return String.format("%s \n%s \n%s \n%s \n%s \n%s \n", name, line1, city, province, postalCode, country);
    }
    return String.format("%s \n%s \n%s \n%s \n%s \n%s \n%s \n",
                         name, line1, line2, city, province, postalCode, country);
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostalAddress postalAddress = (PostalAddress) o;
    return toString().equals(postalAddress.toString());
  }

  public int hashCode() {
    return Objects.hash(toString());
  }

  public String getLine1() {
    return line1;
  }

  public String getLine2() {
    return line2;
  }

  public String getCity() {
    return city;
  }

  public String getProvince() {
    return province;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public String getCountry() {
    return country;
  }
}
