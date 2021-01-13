package ca.ulaval.glo4003.spamdul.parking2.entities.delivery;

public class DeliveryInfosPostal {

  private final int addressNumber;
  private final String street;
  private final int apartmentNumber;
  private final String city;
  private final String country;
  private final String region;
  private final String postalCode;

  public DeliveryInfosPostal(int addressNumber,
                             String street,
                             int apartmentNumber,
                             String city,
                             String country, String region, String postalCode) {
    this.addressNumber = addressNumber;
    this.street = street;
    this.apartmentNumber = apartmentNumber;
    this.city = city;
    this.country = country;
    this.region = region;
    this.postalCode = postalCode;
  }
}
