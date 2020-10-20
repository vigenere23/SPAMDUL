package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.Period;
import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.AccessingCampusRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.AccessingCampusResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.CampusAccessRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.CampusAccessResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.car.CarRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.user.UserRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.timeperiod.dto.TimePeriodRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.car.CarAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.exceptions.InvalidCampusAccessCodeArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.user.UserAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.TimePeriodAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions.InvalidPeriodArgumentException;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.AccessingCampusDto;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.CampusAccessDto;
import java.time.DayOfWeek;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class CampusAccessAssemblerTest {

  private final UserId A_USER_ID = UserId.valueOf("1");
  private final CarId A_CAR_ID = CarId.valueOf("1");
  private final CampusAccessCode A_CAMPUS_ACCESS_CODE = CampusAccessCode.valueOf("1");
  private final DayOfWeek A_DAY_TO_ACCESS_CAMPUS = DayOfWeek.MONDAY;
  private final String A_DAY_TO_ACCESS_CAMPUS_STRING = "monday";
  private final Period A_PERIOD = Period.SEMESTER_1;
  private final String A_PERIOD_STRING = "semester_1";
  private final String A_CAMPUS_ACCESS_CODE_STRING = "1";
  private final String ACCESSING_CAMPUS_DATE_STRING = "2020-01-01";
  public final LocalDate ACCESSING_CAMPUS_DATE = LocalDate.of(2020, 1, 1);

  private CampusAccessAssembler campusAccessAssembler;
  private UserAssembler userAssembler;
  private CarAssembler carAssembler;
  private TimePeriodAssembler timePeriodAssembler;
  private CampusAccessRequest campusAccessRequest;
  private CampusAccess campusAccess;
  private UserRequest userRequest;
  private CarRequest carRequest;
  private AccessingCampusRequest accessingCampusRequest;
  private TimePeriodRequest timePeriodRequest;
  private TimePeriodDto timePeriodDto;

  @Before
  public void setUp() throws Exception {
    userAssembler = mock(UserAssembler.class);
    carAssembler = mock(CarAssembler.class);
    timePeriodAssembler = mock(TimePeriodAssembler.class);
    campusAccess = new CampusAccess(A_CAMPUS_ACCESS_CODE, A_USER_ID, A_CAR_ID, null);
    campusAccessRequest = new CampusAccessRequest();
    campusAccessRequest.car = carRequest;
    campusAccessRequest.user = userRequest;
    campusAccessAssembler = new CampusAccessAssembler(userAssembler, carAssembler, timePeriodAssembler);
    accessingCampusRequest = new AccessingCampusRequest();
    accessingCampusRequest.campusAccessCode = A_CAMPUS_ACCESS_CODE_STRING;
    timePeriodDto = new TimePeriodDto();
    timePeriodDto.periodType = PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER;
    timePeriodRequest = new TimePeriodRequest();
    timePeriodRequest.type = "";
    campusAccessRequest.period = timePeriodRequest;
    when(timePeriodAssembler.fromRequest(timePeriodRequest)).thenReturn(timePeriodDto);
  }

  @Test
  public void whenAssemblingFromRequest_shouldCallUserAssembler() {
    campusAccessAssembler.fromRequest(campusAccessRequest);

    verify(userAssembler, times(1)).fromRequest(campusAccessRequest.user);
  }

  @Test
  public void whenAssemblingFromRequest_shouldCallCarAssembler() {
    campusAccessAssembler.fromRequest(campusAccessRequest);

    verify(carAssembler, times(1)).fromRequest(campusAccessRequest.car);
  }

  @Test
  public void whenAssemblingFromRequest_shouldSetParameterAccordingly() {
    CampusAccessDto campusAccessDto = campusAccessAssembler.fromRequest(campusAccessRequest);

    assertThat(campusAccessDto.timePeriodDto).isEqualTo(timePeriodDto);
  }


  @Test(expected = InvalidPeriodArgumentException.class)
  public void givenAnInvalidPeriod_whenAssemblingFromRequest_shouldThrowInvalidPeriodException() {
    timePeriodDto.periodType = PeriodType.MONTHLY;

    campusAccessAssembler.fromRequest(campusAccessRequest);
  }

  @Test
  public void whenCreatingCampusAccessResponse_shouldCreateResponseWithTheRightInfos() {
    CampusAccessResponse campusAccessResponse = campusAccessAssembler.toResponse(campusAccess);

    assertThat(campusAccessResponse.campusAccessCode).isNotNull();
  }

  @Test
  public void whenAssemblingFromRequest_shouldCreateNewAccessingCampusDto() {
    AccessingCampusDto accessingCampusDto = campusAccessAssembler.fromRequest(accessingCampusRequest);

    assertThat(accessingCampusDto.campusAccessCode).isEqualTo(A_CAMPUS_ACCESS_CODE);
  }


  @Test(expected = InvalidCampusAccessCodeArgumentException.class)
  public void givenAnInvalidCampusAccessCodeFormat_whenAssemblingFromRequest_shouldThrowInvalidCampusAccessCodeArgumentException() {
    accessingCampusRequest.campusAccessCode = "j";

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