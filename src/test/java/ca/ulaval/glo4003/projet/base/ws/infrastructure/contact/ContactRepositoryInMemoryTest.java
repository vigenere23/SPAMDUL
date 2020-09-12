package ca.ulaval.glo4003.projet.base.ws.infrastructure.contact;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.truth.Truth;

import ca.ulaval.glo4003.projet.base.ws.domain.contact.Contact;
import ca.ulaval.glo4003.projet.base.ws.infrastructure.contact.ContactRepositoryInMemory;

@RunWith(MockitoJUnitRunner.class)
public class ContactRepositoryInMemoryTest {

  private static final String CONTACT_ID = "id";

  @Mock
  private Contact contact;

  private ContactRepositoryInMemory contactRepositoryInMemory;

  @Before
  public void setUp() {
    contactRepositoryInMemory = new ContactRepositoryInMemory();
    BDDMockito.given(contact.getId()).willReturn(CONTACT_ID);
  }

  @Test
  public void givenContact_whenFindAll_ThenReturnContactInMemory() {
    //given
    contactRepositoryInMemory.save(contact);

    // when
    List<Contact> contacts = contactRepositoryInMemory.findAll();

    // then
    Truth.assertThat(contacts).contains(contact);
  }

}