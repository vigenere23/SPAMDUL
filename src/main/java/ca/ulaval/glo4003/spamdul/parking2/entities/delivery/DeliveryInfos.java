package ca.ulaval.glo4003.spamdul.parking2.entities.delivery;

public class DeliveryInfos {

  private final String emailAddress;
  private final int addressNumber;
  private final String street;
  private final int apartmentNumber;
  private final String city;
  private final String country;
  private final String region;
  private final String postalCode;

  public DeliveryInfos(String emailAddress,
                       int addressNumber,
                       String street,
                       int apartmentNumber,
                       String city, String country, String region, String postalCode) {
    this.emailAddress = emailAddress;
    this.addressNumber = addressNumber;
    this.street = street;
    this.apartmentNumber = apartmentNumber;
    this.city = city;
    this.country = country;
    this.region = region;
    this.postalCode = postalCode;
  }

  public DeliveryInfosEmail forEmail() {
    return new DeliveryInfosEmail(emailAddress);
  }

  public DeliveryInfosPostal forPostal() {
    return new DeliveryInfosPostal(addressNumber, street, apartmentNumber, city, country, region, postalCode);
  }
}
