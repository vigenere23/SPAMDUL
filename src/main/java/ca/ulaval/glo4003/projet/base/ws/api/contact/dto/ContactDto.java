package ca.ulaval.glo4003.projet.base.ws.api.contact.dto;

public class ContactDto {
  public String id;
  public String telephoneNumber;
  public String address;
  public String name;

  @Override
  public String toString() {
    return "ContactDto{" +
            "id='" + id + '\'' +
            ", telephoneNumber='" + telephoneNumber + '\'' +
            ", address='" + address + '\'' +
            ", name='" + name + '\'' +
            '}';
  }
}
