package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.entity.user.Gender;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.AccessingCampusRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.AccessingCampusResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.CampusAccessRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.CampusAccessResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.timeperiod.dto.TimePeriodRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.car.CarAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.exceptions.InvalidAccessingCampusArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.exceptions.InvalidCampusAccessCodeFormatException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.user.UserAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.TimePeriodAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions.InvalidTimePeriodArgumentException;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.AccessingCampusDto;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.CampusAccessDto;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CampusAccessAssemblerTest {

  private final UserId A_USER_ID = new UserId();
  private final String A_NAME = "name";
  private final Gender A_GENDER = Gender.MALE;
  private final LocalDate A_BIRTHDAY_DATE = LocalDate.of(2010, 1, 1);
  private final User A_USER = new User(A_USER_ID, A_NAME, A_GENDER, A_BIRTHDAY_DATE);
  private final String A_CAR_BRAND = "brand";
  private final String A_CAR_MODEL = "model";
  private final String A_LICENSE_PLATE_STRING = "license plate";
  private final LicensePlate A_LICENSE_PLATE = new LicensePlate(A_LICENSE_PLATE_STRING);
  private final CarId A_CAR_ID = CarId.valueOf("1");
  private final CarType A_CAR_TYPE = CarType.ECONOMIQUE;
  private final int A_YEAR = 2020;
  private final Car A_CAR = new Car(A_CAR_ID, A_CAR_TYPE, A_CAR_BRAND, A_CAR_MODEL, A_YEAR, A_LICENSE_PLATE);
  public static final PeriodType INVALID_PERIOD_TYPE = PeriodType.MONTHLY;
  public static final String INVALID_ACCESS_CODE_FORMAT = "j";
  private final CampusAccessCode A_CAMPUS_ACCESS_CODE = CampusAccessCode.valueOf("1");
  private final String A_CAMPUS_ACCESS_CODE_STRING = "1";

  private CampusAccessAssembler campusAccessAssembler;
  @Mock
  private UserAssembler userAssembler;
  @Mock
  private CarAssembler carAssembler;
  @Mock
  private TimePeriodAssembler timePeriodAssembler;
  private CampusAccessRequest campusAccessRequest;
  private CampusAccess campusAccess;
  private AccessingCampusRequest accessingCampusRequest;
  private TimePeriodRequest timePeriodRequest;
  private TimePeriodDto timePeriodDto;

  @Before
  public void setUp() throws Exception {
    campusAccess = new CampusAccess(A_CAMPUS_ACCESS_CODE, A_USER, A_CAR, null, null);
    campusAccessRequest = new CampusAccessRequest();
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

  @Test(expected = InvalidCampusAccessCodeFormatException.class)
  public void givenAnInvalidCampusAccessCodeFormat_whenAssemblingFromRequest_shouldThrowInvalidCampusAccessCodeArgumentException() {
    accessingCampusRequest.campusAccessCode = INVALID_ACCESS_CODE_FORMAT;

    campusAccessAssembler.fromRequest(accessingCampusRequest);
  }

  @Test(expected = InvalidAccessingCampusArgumentException.class)
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