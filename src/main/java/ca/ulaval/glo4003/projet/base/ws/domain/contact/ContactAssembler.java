package ca.ulaval.glo4003.projet.base.ws.domain.contact;

import ca.ulaval.glo4003.projet.base.ws.api.contact.dto.ContactDto;

public class ContactAssembler {
  public Contact create(ContactDto contactDto) {
    Contact contact = new Contact();
    contact.setAddress(contactDto.address);
    contact.setTelephoneNumber(contactDto.telephoneNumber);
    contact.setName(contactDto.name);
    return contact;
  }

  public ContactDto create(Contact contact) {
    ContactDto contactDto = new ContactDto();
    contactDto.address = contact.getAddress();
    contactDto.telephoneNumber = contact.getTelephoneNumber();
    contactDto.name = contact.getName();
    contactDto.id = contact.getId();
    return contactDto;
  }
}
