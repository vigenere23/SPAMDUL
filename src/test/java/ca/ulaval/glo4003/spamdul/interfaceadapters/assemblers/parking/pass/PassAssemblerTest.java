package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.pass;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.InvalidUserIdFormatException;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.pass.dto.DeliveryRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.pass.dto.PassCreationRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.timeperiod.dto.TimePeriodRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.DeliveryAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.pass.exceptions.InvalidParkingZoneException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.TimePeriodAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions.InvalidPeriodArgumentException;
import ca.ulaval.glo4003.spamdul.usecases.parking.pass.DeliveryDto;
import ca.ulaval.glo4003.spamdul.usecases.parking.pass.PassDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PassAssemblerTest {

  private static final String A_PARKING_ZONE_STRING = "zone_1";
  private static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_1;
  private static final UserId A_USER_ID = new UserId();
  private static final String A_USER_ID_STRING = A_USER_ID.toString();
  private static final TimePeriodRequest A_TIME_PERIOD_REQUEST = new TimePeriodRequest();
  private static final PassCreationRequest A_PASS_CREATION_REQUEST = new PassCreationRequest();
  private static final DeliveryRequest A_DELIVERY_REQUEST = new DeliveryRequest();
  private static final DeliveryDto A_DELIVERY_DTO = new DeliveryDto();

  private final TimePeriodDto timePeriodDto = new TimePeriodDto();
  @Mock
  private TimePeriodAssembler timePeriodAssembler;
  private PassAssembler passAssembler;
  @Mock
  private DeliveryAssembler deliveryAssembler;

  @Before
  public void setUp() {
    A_PASS_CREATION_REQUEST.parkingZone = A_PARKING_ZONE_STRING;
    A_PASS_CREATION_REQUEST.userId = A_USER_ID_STRING;
    A_PASS_CREATION_REQUEST.delivery = A_DELIVERY_REQUEST;
    A_PASS_CREATION_REQUEST.period = A_TIME_PERIOD_REQUEST;

    passAssembler = new PassAssembler(deliveryAssembler, timePeriodAssembler);

    timePeriodDto.periodType = PeriodType.MONTHLY;
    when(deliveryAssembler.fromRequest(A_DELIVERY_REQUEST)).thenReturn(A_DELIVERY_DTO);
    when(timePeriodAssembler.fromRequest(A_TIME_PERIOD_REQUEST)).thenReturn(timePeriodDto);
  }

  @Test
  public void whenAssemblingFromRequest_thenShouldCreatePassDtoWithRightFields() {
    PassDto passDto = passAssembler.fromRequest(A_PASS_CREATION_REQUEST);

    assertThat(passDto.parkingZone).isEqualTo(A_PARKING_ZONE);
    assertThat(passDto.timePeriodDto).isEqualTo(timePeriodDto);
    assertThat(passDto.userId).isEqualTo(A_USER_ID);
    assertThat(passDto.deliveryDto).isEqualTo(A_DELIVERY_DTO);
  }

  @Test(expected = InvalidParkingZoneException.class)
  public void givenInvalidParkingZone_whenAssemblingFromRequest_thenShouldThrowInvalidParkingZoneException() {
    A_PASS_CREATION_REQUEST.parkingZone = "invalid";

    passAssembler.fromRequest(A_PASS_CREATION_REQUEST);
  }

  @Test(expected = InvalidUserIdFormatException.class)
  public void givenInvalidCampusAccessCode_whenAssemblingFromRequest_thenShouldThrowInvalidException() {
    A_PASS_CREATION_REQUEST.userId = "invalid";

    passAssembler.fromRequest(A_PASS_CREATION_REQUEST);
  }

  @Test(expected = InvalidPeriodArgumentException.class)
  public void givenAnInvalidArgumentWhileAssemblingTimePeriod_whenAssemblingFromRequest_shouldThrow() {
    when(timePeriodAssembler.fromRequest(A_TIME_PERIOD_REQUEST)).thenThrow(new IllegalArgumentException());

    passAssembler.fromRequest(A_PASS_CREATION_REQUEST);
  }
}
