package ca.ulaval.glo4003.projet.base.ws.entity.contact;

import ca.ulaval.glo4003.projet.base.ws.infrastructure.ui.contact.dto.ContactDto;
import com.google.common.truth.Truth;
import java.util.List;
import jersey.repackaged.com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

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