package ca.ulaval.glo4003.spamdul.parking2.usecases.dtos;

import ca.ulaval.glo4003.spamdul.parking2.entities.delivery.DeliveryType;

public class PermitDeliveryDto {

  public DeliveryType type;
  public String emailAddress;
  public int addressNumber;
  public String street;
  public int apartmentNumber;
  public String city;
  public String country;
  public String region;
  public String postalCode;
}
