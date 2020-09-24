package ca.ulaval.glo4003.spamdul.entity.contact;

import java.util.List;

public interface ContactRepository {

  List<Contact> findAll();

  Contact findById(String id);

  void update(Contact contact)
      throws ContactNotFoundException;

  void save(Contact contact);

  void remove(String id);
}
