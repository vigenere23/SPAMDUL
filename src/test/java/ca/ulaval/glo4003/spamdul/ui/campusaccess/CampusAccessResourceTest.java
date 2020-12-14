package ca.ulaval.glo4003.spamdul.ui.campusaccess;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.ui.campusaccess.dto.AccessingCampusRequest;
import ca.ulaval.glo4003.spamdul.ui.campusaccess.dto.CampusAccessRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess.CampusAccessAssembler;
import ca.ulaval.glo4003.spamdul.usecases.parking.campusaccess.dto.AccessingCampusDto;
import ca.ulaval.glo4003.spamdul.usecases.parking.campusaccess.dto.CampusAccessDto;
import ca.ulaval.glo4003.spamdul.usecases.parking.campusaccess.CampusAccessService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CampusAccessResourceTest {

  private final String A_CAMPUS_ACCESS_CODE_STRING = "1";
  private final CampusAccessCode A_CAMPUS_ACCESS_CODE = CampusAccessCode.valueOf(A_CAMPUS_ACCESS_CODE_STRING);

  @Mock
  private CampusAccessAssembler campusAccessAssembler;
  @Mock
  private CampusAccessService campusAccessService;
  private CampusAccessResource campusAccessResource;
  private CampusAccessRequest campusAccessRequest;
  private CampusAccessDto requestCampusAccessDto;
  private CampusAccessDto returnCampusAccessDto;
  private AccessingCampusRequest accessingCampusRequest;
  private AccessingCampusDto accessingCampusDto;

  @Before
  public void setUp() throws Exception {
    requestCampusAccessDto = new CampusAccessDto();
    campusAccessRequest = new CampusAccessRequest();
    returnCampusAccessDto = new CampusAccessDto();
    campusAccessResource = new CampusAccessResource(campusAccessAssembler, campusAccessService);
    accessingCampusRequest = new AccessingCampusRequest();
    accessingCampusRequest.campusAccessCode = A_CAMPUS_ACCESS_CODE_STRING;
    accessingCampusDto = new AccessingCampusDto();
    accessingCampusDto.campusAccessCode = A_CAMPUS_ACCESS_CODE;
  }

  @Test
  public void whenCreatingNewCampusAccess_shouldCallAssemblerToMapCampusAccessInfos() {
    campusAccessResource.createCampusAccess(campusAccessRequest);

    verify(campusAccessAssembler, times(1)).fromRequest(campusAccessRequest);
  }

  @Test
  public void whenCreatingNewCampusAccess_shouldCallServiceToCreateNewCampusAccess() {
    when(campusAccessAssembler.fromRequest(campusAccessRequest)).thenReturn(requestCampusAccessDto);

    campusAccessResource.createCampusAccess(campusAccessRequest);

    verify(campusAccessService, times(1)).createCampusAccess(requestCampusAccessDto);
  }

  @Test
  public void whenCreatingNewCampusAccess_shouldCallAssemblerToCreateCampusAccessResponse() {
    when(campusAccessAssembler.fromRequest(campusAccessRequest)).thenReturn(requestCampusAccessDto);
    when(campusAccessService.createCampusAccess(requestCampusAccessDto)).thenReturn(returnCampusAccessDto);

    campusAccessResource.createCampusAccess(campusAccessRequest);

    verify(campusAccessAssembler, times(1)).toResponse(returnCampusAccessDto);
  }

  @Test
  public void whenVerifyingIfCanAccessCampus_shouldCallAssembler() {
    campusAccessResource.canAccessCampus(accessingCampusRequest);

    verify(campusAccessAssembler, times(1)).fromRequest(accessingCampusRequest);
  }

  @Test
  public void whenVerifyingIfCanAccessCampus_shouldCallServiceToVerifyingIfCanAccess() {
    given(campusAccessAssembler.fromRequest(accessingCampusRequest)).willReturn(accessingCampusDto);
    campusAccessResource.canAccessCampus(accessingCampusRequest);

    verify(campusAccessService, times(1)).grantAccessToCampus(accessingCampusDto);
  }

  @Test
  public void whenVerifyingIfCanAccessCampus_shouldAssembleResponse() {
    when(campusAccessAssembler.fromRequest(accessingCampusRequest)).thenReturn(accessingCampusDto);
    when(campusAccessService.grantAccessToCampus(accessingCampusDto)).thenReturn(true);

    campusAccessResource.canAccessCampus(accessingCampusRequest);

    verify(campusAccessAssembler, times(1)).toResponse(true);
  }
}
