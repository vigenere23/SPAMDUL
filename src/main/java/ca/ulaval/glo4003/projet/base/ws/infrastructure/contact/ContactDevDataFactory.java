package ca.ulaval.glo4003.projet.base.ws.infrastructure.contact;

import jersey.repackaged.com.google.common.collect.Lists;

import java.util.List;

import ca.ulaval.glo4003.projet.base.ws.domain.contact.Contact;

public class ContactDevDataFactory {

  public List<Contact> createMockData() {
    List<Contact> contacts = Lists.newArrayList();
    Contact jobs = new Contact();
    jobs.setId("1");
    jobs.setName("Steve Jobs");
    jobs.setAddress("California");
    jobs.setTelephoneNumber("514-999-0000");
    contacts.add(jobs);

    Contact balmer = new Contact();
    balmer.setId("2");
    balmer.setName("Steve Balmer");
    balmer.setAddress("Manitoba");
    balmer.setTelephoneNumber("781-888-1111");
    contacts.add(balmer);

    Contact franklin = new Contact();
    franklin.setId("3");
    franklin.setName("Benjamin Franklin");
    franklin.setAddress("Washington");
    franklin.setTelephoneNumber("964-543-6475");
    contacts.add(franklin);

    return contacts;
  }

}
