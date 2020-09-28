package ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.AccessingCampusRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.CampusAccessRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.CampusAccessAssembler;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.AccessingCampusDto;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.CampusAccessDto;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.CampusAccessService;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class CampusAccessResourceImplTest {

  private final String A_CAMPUS_ACCESS_CODE_STRING = "1";
  private final CampusAccessCode A_CAMPUS_ACCESS_CODE = CampusAccessCode.valueOf(A_CAMPUS_ACCESS_CODE_STRING);
  private final String ACCESSING_CAMPUS_DATE_STRING = "2020-01-01";
  private final LocalDate ACCESSING_CAMPUS_DATE = LocalDate.of(2020, 1, 1);

  private CampusAccessAssembler campusAccessAssembler;
  private CampusAccessService campusAccessService;
  private CampusAccessResourceImpl campusAccessResource;
  private CampusAccessRequest campusAccessRequest;
  private CampusAccessDto campusAccessDto;
  private CampusAccess campusAccess;
  private AccessingCampusRequest accessingCampusRequest;
  private AccessingCampusDto accessingCampusDto;

  @Before
  public void setUp() throws Exception {
    campusAccessAssembler = mock(CampusAccessAssembler.class);
    campusAccessService = mock(CampusAccessService.class);
    campusAccessDto = new CampusAccessDto();
    campusAccessRequest = new CampusAccessRequest();
    campusAccess = new CampusAccess(null, null, null, null, null);
    campusAccessResource = new CampusAccessResourceImpl(campusAccessAssembler, campusAccessService);
    accessingCampusRequest = new AccessingCampusRequest();
    accessingCampusRequest.campusAccessCode = A_CAMPUS_ACCESS_CODE_STRING;
    accessingCampusRequest.accessingCampusDate = ACCESSING_CAMPUS_DATE_STRING;
    accessingCampusDto = new AccessingCampusDto();
    accessingCampusDto.accessingCampusDate = ACCESSING_CAMPUS_DATE;
    accessingCampusDto.campusAccessCode = A_CAMPUS_ACCESS_CODE;
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

    verify(campusAccessService, times(1)).createAndSaveNewCampusAccess(campusAccessDto);
  }

  @Test
  public void whenCreatingNewCampusAccess_shouldCallAssemblerToCreateCampusAccessResponse() {
    given(campusAccessAssembler.fromRequest(campusAccessRequest)).willReturn(campusAccessDto);
    given(campusAccessService.createAndSaveNewCampusAccess(campusAccessDto)).willReturn(campusAccess);

    campusAccessResource.createCampusAccess(campusAccessRequest);

    verify(campusAccessAssembler, times(1)).toResponse(campusAccess);
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

    verify(campusAccessService, times(1)).canAccessCampus(accessingCampusDto);
  }

  @Test
  public void whenVerifyingIfCanAccessCampus_shouldAssembleResponse() {
    given(campusAccessAssembler.fromRequest(accessingCampusRequest)).willReturn(accessingCampusDto);
    given(campusAccessService.canAccessCampus(accessingCampusDto)).willReturn(true);

    campusAccessResource.canAccessCampus(accessingCampusRequest);

    verify(campusAccessAssembler, times(1)).toResponse(true);
  }
}