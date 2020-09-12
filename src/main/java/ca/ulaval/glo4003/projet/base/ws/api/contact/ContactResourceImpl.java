package ca.ulaval.glo4003.projet.base.ws.api.contact;


import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4003.projet.base.ws.api.contact.dto.ContactDto;
import ca.ulaval.glo4003.projet.base.ws.domain.contact.ContactNotFoundException;
import ca.ulaval.glo4003.projet.base.ws.domain.contact.ContactService;

import java.util.List;

public class ContactResourceImpl implements ContactResource {

  private ContactService contactService;

  public ContactResourceImpl(ContactService contactService) {
    this.contactService = contactService;
  }

  @Override
  public List<ContactDto> getContacts() {
    return contactService.findAllContacts();
  }

  @Override
  public ContactDto getContact(String id) {
    return contactService.findContact(id);
  }

  @Override
  public void addContact(ContactDto contactDto) {
    contactService.addContact(contactDto);
  }

  @Override
  public void updateContact(String id, ContactDto contactDto) {
    try {
      contactService.updateContact(id, contactDto);
    } catch (ContactNotFoundException e) {
      throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                                                .entity(e.getMessage())
                                                .build());
    }
  }

  @Override
  public void deleteContact(String id) {
    contactService.deleteContact(id);
  }
}
