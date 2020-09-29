package ca.ulaval.glo4003.spamdul.infrastructure.ui.contact;

import ca.ulaval.glo4003.spamdul.entity.contact.ContactService;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.contact.dto.ContactDto;
import com.google.common.truth.Truth;
import java.util.List;
import jersey.repackaged.com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class ContactResourceImplTest {

  @Mock
  private ContactService contactService;
  @Mock
  private ContactDto contactDto;

  private ContactResource contactResource;


  @Before
  public void setUp()
      throws Exception {
    contactResource = new ContactResourceImpl(contactService);
  }

  @Test
  public void whenFindAllContacts_thenFoundContactsFromService() {
    // given
    BDDMockito.given(contactService.findAllContacts()).willReturn(Lists.newArrayList(contactDto));

    // when
    List<ContactDto> contactDtos = contactResource.getContacts();

    // then
    Truth.assertThat(contactDtos).contains(contactDto);
  }

}