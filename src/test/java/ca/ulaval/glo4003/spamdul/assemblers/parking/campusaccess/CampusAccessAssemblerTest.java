package ca.ulaval.glo4003.spamdul.assemblers.parking.campusaccess;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.assemblers.parking.campusaccess.exceptions.InvalidCampusAccessArgumentException;
import ca.ulaval.glo4003.spamdul.assemblers.timeperiod.TimePeriodAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.timeperiod.exceptions.InvalidTimePeriodArgumentException;
import ca.ulaval.glo4003.spamdul.parking.api.campusaccess.dto.AccessingCampusRequest;
import ca.ulaval.glo4003.spamdul.parking.api.campusaccess.dto.AccessingCampusResponse;
import ca.ulaval.glo4003.spamdul.parking.api.campusaccess.dto.CampusAccessRequest;
import ca.ulaval.glo4003.spamdul.parking.api.campusaccess.dto.CampusAccessResponse;
import ca.ulaval.glo4003.spamdul.parking.entities.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking.usecases.campusaccess.dto.AccessingCampusDto;
import ca.ulaval.glo4003.spamdul.parking.usecases.campusaccess.dto.CampusAccessDto;
import ca.ulaval.glo4003.spamdul.time.api.timeperiod.TimePeriodRequest;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.TimePeriodDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CampusAccessAssemblerTest {


  private final String A_LICENSE_PLATE_STRING = "license plate";
  private final LicensePlate A_LICENSE_PLATE = new LicensePlate(A_LICENSE_PLATE_STRING);
  public static final PeriodType INVALID_PERIOD_TYPE = PeriodType.MONTHLY;
  private final CampusAccessCode A_CAMPUS_ACCESS_CODE = CampusAccessCode.valueOf("1");
  private final String A_CAMPUS_ACCESS_CODE_STRING = "1";

  private CampusAccessAssembler campusAccessAssembler;
  @Mock
  private TimePeriodAssembler timePeriodAssembler;
  private CampusAccessRequest campusAccessRequest;
  private CampusAccessDto campusAccess;
  private AccessingCampusRequest accessingCampusRequest;
  private TimePeriodRequest timePeriodRequest;
  private TimePeriodDto timePeriodDto;

  @Before
  public void setUp() throws Exception {
    campusAccess = new CampusAccessDto();
    campusAccess.code = A_CAMPUS_ACCESS_CODE;
    campusAccessRequest = new CampusAccessRequest();
    campusAccessAssembler = new CampusAccessAssembler(timePeriodAssembler);
    accessingCampusRequest = new AccessingCampusRequest();
    accessingCampusRequest.campusAccessCode = A_CAMPUS_ACCESS_CODE_STRING;
    timePeriodDto = new TimePeriodDto();
    timePeriodDto.periodType = PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER;
    timePeriodRequest = new TimePeriodRequest();
    timePeriodRequest.type = "";
    campusAccessRequest.period = timePeriodRequest;
    campusAccessRequest.userId = "1";
    when(timePeriodAssembler.fromRequest(timePeriodRequest)).thenReturn(timePeriodDto);
  }

  @Test
  public void whenAssemblingFromRequest_shouldSetParameterAccordingly() {
    CampusAccessDto campusAccessDto = campusAccessAssembler.fromRequest(campusAccessRequest);

    assertThat(campusAccessDto.timePeriodDto).isEqualTo(timePeriodDto);
  }


  @Test(expected = InvalidTimePeriodArgumentException.class)
  public void givenAnInvalidPeriod_whenAssemblingFromRequest_shouldThrowInvalidPeriodException() {
    timePeriodDto.periodType = INVALID_PERIOD_TYPE;

    campusAccessAssembler.fromRequest(campusAccessRequest);
  }

  @Test
  public void whenCreatingCampusAccessResponse_shouldCreateResponseWithTheRightInfos() {
    CampusAccessResponse campusAccessResponse = campusAccessAssembler.toResponse(campusAccess);

    assertThat(campusAccessResponse.campusAccessCode).isNotNull();
  }

  @Test
  public void givenARequestWithCampusAccessCode_whenAssemblingFromRequest_shouldCreateNewAccessingCampusDto() {
    AccessingCampusDto accessingCampusDto = campusAccessAssembler.fromRequest(accessingCampusRequest);

    assertThat(accessingCampusDto.campusAccessCode).isEqualTo(A_CAMPUS_ACCESS_CODE);
  }

  @Test
  public void givenARequestWithLicensePlate_whenAssemblingFromRequest_shouldCreateNewAccessingCampusDto() {
    accessingCampusRequest.campusAccessCode = null;
    accessingCampusRequest.licensePlate = A_LICENSE_PLATE_STRING;

    AccessingCampusDto accessingCampusDto = campusAccessAssembler.fromRequest(accessingCampusRequest);

    assertThat(accessingCampusDto.licensePlate).isEqualTo(A_LICENSE_PLATE);
  }

  @Test(expected = InvalidCampusAccessArgumentException.class)
  public void givenNoParamToAccessCampus_whenAssemblingFromRequest_shouldThrowInvalidCampusAccessCodeArgumentException() {
    accessingCampusRequest.campusAccessCode = null;
    accessingCampusRequest.licensePlate = null;

    campusAccessAssembler.fromRequest(accessingCampusRequest);
  }

  @Test
  public void givenAccessIsGranted_whenAssemblingResponse_shouldTellAccessGranted() {
    AccessingCampusResponse response = campusAccessAssembler.toResponse(true);

    assertThat(response.access).isEqualTo("GRANTED");
  }

  @Test
  public void givenAccessIsNotGranted_whenAssemblingResponse_shouldTellAccessIsNotGranted() {
    AccessingCampusResponse response = campusAccessAssembler.toResponse(false);

    assertThat(response.access).isEqualTo("NOT GRANTED");
  }
}
