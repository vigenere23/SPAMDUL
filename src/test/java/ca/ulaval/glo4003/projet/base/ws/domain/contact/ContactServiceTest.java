package ca.ulaval.glo4003.projet.base.ws.domain.contact;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.truth.Truth;

import ca.ulaval.glo4003.projet.base.ws.api.contact.dto.ContactDto;
import ca.ulaval.glo4003.projet.base.ws.domain.contact.Contact;
import ca.ulaval.glo4003.projet.base.ws.domain.contact.ContactAssembler;
import ca.ulaval.glo4003.projet.base.ws.domain.contact.ContactRepository;
import ca.ulaval.glo4003.projet.base.ws.domain.contact.ContactService;
import jersey.repackaged.com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class ContactServiceTest {

  @Mock
  private Contact contact;
  @Mock
  private ContactDto contactDto;
  @Mock
  private ContactRepository contactRepository;
  @Mock
  private ContactAssembler contactAssembler;

  private ContactService contactService;

  @Before
  public void setUp()
          throws Exception {
    contactService = new ContactService(contactRepository, contactAssembler);
  }

  @Test
  public void givenContactsInRepository_whenFindAllContacts_thenReturnThose()
          throws Exception {
    // given
    BDDMockito.given(contactRepository.findAll()).willReturn(Lists.newArrayList(contact));
    BDDMockito.given(contactAssembler.create(contact)).willReturn(contactDto);

    // when
    List<ContactDto> contactDtos = contactService.findAllContacts();

    // then
    Truth.assertThat(contactDtos).contains(contactDto);
    Mockito.verify(contactRepository).findAll();
    Mockito.verify(contactAssembler).create(org.mockito.Matchers.eq(contact));
  }

}