package ca.ulaval.glo4003.spamdul.infrastructure.ui.accesscampus;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.accesscampus.dto.CampusAccessRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.CampusAccessAssembler;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.CampusAccessDto;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.CampusAccessService;
import org.junit.Before;
import org.junit.Test;

public class CampusAccessResourceImplTest {

  private CampusAccessAssembler campusAccessAssembler;
  private CampusAccessService campusAccessService;
  private CampusAccessResourceImpl campusAccessResource;
  private CampusAccessRequest campusAccessRequest;
  private CampusAccessDto campusAccessDto;
  private CampusAccess campusAccess;

  @Before
  public void setUp() throws Exception {
    campusAccessAssembler = mock(CampusAccessAssembler.class);
    campusAccessService = mock(CampusAccessService.class);
    campusAccessDto = new CampusAccessDto();
    campusAccessRequest = new CampusAccessRequest();
    campusAccess = new CampusAccess(null, null, null, null, null);
    campusAccessResource = new CampusAccessResourceImpl(campusAccessAssembler, campusAccessService);
  }

  @Test
  public void whenCreatingNewCampusAccess_shouldCallAssemblerToMapCampusAccessInfos() {
    campusAccessResource.createCampusAccess(campusAccessRequest);

    verify(campusAccessAssembler, times(1)).fromRequest(campusAccessRequest);
  }

  @Test
  public void whenCreatingNewCampusAccess_shouldCallServiceToCreateNewCampusAccess() {
    given(campusAccessAssembler.fromRequest(campusAccessRequest)).willReturn(campusAccessDto);

    campusAccessResource.createCampusAccess(campusAccessRequest);

    verify(campusAccessService, times(1)).createNewCampusAccess(campusAccessDto);
  }

  @Test
  public void whenCreatingNewCampusAccess_shouldCallAssemblerToCreateCampusAccessResponse() {
    given(campusAccessAssembler.fromRequest(campusAccessRequest)).willReturn(campusAccessDto);
    given(campusAccessService.createNewCampusAccess(campusAccessDto)).willReturn(campusAccess);

    campusAccessResource.createCampusAccess(campusAccessRequest);

    verify(campusAccessAssembler, times(1)).toResponse(campusAccess);
  }

  //TODO ajouter un test qui valide le tout genre voici le request et assert le response?
}