package ca.ulaval.glo4003.spamdul.infrastructure.ui.contact;


import ca.ulaval.glo4003.spamdul.entity.contact.ContactNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.contact.ContactService;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.contact.dto.ContactDto;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ContactResourceImpl implements ContactResource {

  private final ContactService contactService;

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
