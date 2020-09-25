package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.Period;
import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.accesscampus.dto.CampusAccessRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.accesscampus.dto.CampusAccessResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.accesscampus.dto.car.CarRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.accesscampus.dto.user.UserRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.campusaccessexceptions.InvalidDayOfCampusAccessArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.campusaccessexceptions.InvalidPeriodArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.car.CarAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.user.UserAssembler;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.CampusAccessDto;
import java.time.DayOfWeek;
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

  private CampusAccessAssembler campusAccessAssembler;
  private UserAssembler userAssembler;
  private CarAssembler carAssembler;
  private CampusAccessRequest campusAccessRequest;
  private CampusAccess campusAccess;
  private UserRequest userRequest;
  private CarRequest carRequest;

  @Before
  public void setUp() throws Exception {
    userAssembler = mock(UserAssembler.class);
    carAssembler = mock(CarAssembler.class);
    campusAccess = new CampusAccess(A_CAMPUS_ACCESS_CODE, A_USER_ID, A_CAR_ID, A_DAY_TO_ACCESS_CAMPUS, A_PERIOD);
    campusAccessRequest = new CampusAccessRequest();
    campusAccessRequest.carRequest = carRequest;
    campusAccessRequest.userRequest = userRequest;
    campusAccessRequest.dayToAccessCampus = A_DAY_TO_ACCESS_CAMPUS_STRING;
    campusAccessRequest.period = A_PERIOD_STRING;
    campusAccessAssembler = new CampusAccessAssembler(userAssembler, carAssembler);
  }

  @Test
  public void whenAssemblingFromRequest_shouldCallUserAssembler() {
    campusAccessAssembler.fromRequest(campusAccessRequest);

    verify(userAssembler, times(1)).fromRequest(campusAccessRequest.userRequest);
  }

  @Test
  public void whenAssemblingFromRequest_shouldCallCarAssembler() {
    campusAccessAssembler.fromRequest(campusAccessRequest);

    verify(carAssembler, times(1)).fromRequest(campusAccessRequest.carRequest);
  }

  @Test
  public void whenAssemblingFromRequest_shouldSetParameterAccordingly() {
    CampusAccessDto campusAccessDto = campusAccessAssembler.fromRequest(campusAccessRequest);

    assertThat(campusAccessDto.dayToAccessCampus).isEqualTo(A_DAY_TO_ACCESS_CAMPUS);
    assertThat(campusAccessDto.period).isEqualTo(A_PERIOD);
  }

  @Test(expected = InvalidDayOfCampusAccessArgumentException.class)
  public void givenAnInvalidDayToAccessCampus_whenAssemblingFromRequest_shouldThrowInvalidDayToAccessCampusException() {
    campusAccessRequest.dayToAccessCampus = "invalid";

    campusAccessAssembler.fromRequest(campusAccessRequest);
  }

  @Test(expected = InvalidPeriodArgumentException.class)
  public void givenAnInvalidPeriod_whenAssemblingFromRequest_shouldthrowInvalidPeriodException() {
    campusAccessRequest.period = "invalid";

    campusAccessAssembler.fromRequest(campusAccessRequest);
  }

  @Test
  public void whenCreatingCampusAccessResponse_shouldCreateResponseWithTheRightInfos() {
    CampusAccessResponse campusAccessResponse = campusAccessAssembler.toResponse(campusAccess);

    assertThat(campusAccessResponse.getAccessCode()).isNotNull();
  }
}