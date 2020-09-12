package ca.ulaval.glo4003.projet.base.ws.infrastructure.contact;

import jersey.repackaged.com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ulaval.glo4003.projet.base.ws.domain.contact.Contact;
import ca.ulaval.glo4003.projet.base.ws.domain.contact.ContactNotFoundException;
import ca.ulaval.glo4003.projet.base.ws.domain.contact.ContactRepository;

public class ContactRepositoryInMemory implements ContactRepository {

  private Map<String, Contact> contacts = new HashMap<>();

  @Override
  public List<Contact> findAll() {
      return Lists.newArrayList(contacts.values());
  }

  @Override
  public Contact findById(String id) {
    return contacts.get(id);
  }

  @Override
  public void update(Contact contact)
          throws ContactNotFoundException {
    Contact foundContact = contacts.get(contact.getId());
    if (foundContact != null) {
      contacts.put(contact.getId(), contact);
    } else {
      throw new ContactNotFoundException("Contact not found, cannot be updated");
    }
  }

  @Override
  public void save(Contact contact) {
    contacts.put(contact.getId(), contact);
  }

  @Override
  public void remove(String id) {
    contacts.remove(id);
  }
}
