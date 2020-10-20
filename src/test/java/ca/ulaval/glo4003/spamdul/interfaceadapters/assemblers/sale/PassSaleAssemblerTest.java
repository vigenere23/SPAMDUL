package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.dto.DeliveryRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.dto.PassSaleRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.timeperiod.dto.TimePeriodRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.DeliveryAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidCampusAccessCodeExceptionSale;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidParkingZoneExceptionSale;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.TimePeriodAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions.InvalidPeriodArgumentException;
import ca.ulaval.glo4003.spamdul.usecases.pass.DeliveryDto;
import ca.ulaval.glo4003.spamdul.usecases.pass.PassDto;
import org.junit.Before;
import org.junit.Test;

public class PassSaleAssemblerTest {

  private static final String A_PARKING_ZONE_STRING = "zone_1";
  private static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_1;
  private static final CampusAccessCode AN_ACCESS_CODE = new CampusAccessCode();
  private static final String AN_ACCESS_CODE_STRING = AN_ACCESS_CODE.toString();
  private static final TimePeriodRequest A_TIME_PERIOD_REQUEST = new TimePeriodRequest();
  private static final PassSaleRequest A_PASS_SALE_REQUEST = new PassSaleRequest();
  private static final DeliveryRequest A_DELIVERY_REQUEST = new DeliveryRequest();
  private static final DeliveryDto A_DELIVERY_DTO = new DeliveryDto();

  private TimePeriodDto timePeriodDto = new TimePeriodDto();
  private TimePeriodAssembler timePeriodAssembler;
  private PassSaleAssembler passSaleAssembler;
  private DeliveryAssembler deliveryAssembler;

  @Before
  public void setUp() {
    A_PASS_SALE_REQUEST.parkingZone = A_PARKING_ZONE_STRING;
    A_PASS_SALE_REQUEST.campusAccessCode = AN_ACCESS_CODE_STRING;
    A_PASS_SALE_REQUEST.deliveryInfos = A_DELIVERY_REQUEST;
    A_PASS_SALE_REQUEST.period = A_TIME_PERIOD_REQUEST;

    deliveryAssembler = mock(DeliveryAssembler.class);
    timePeriodAssembler = mock(TimePeriodAssembler.class);
    passSaleAssembler = new PassSaleAssembler(deliveryAssembler, timePeriodAssembler);

    timePeriodDto.periodType = PeriodType.MONTHLY;
    given(deliveryAssembler.fromRequest(A_DELIVERY_REQUEST)).willReturn(A_DELIVERY_DTO);
    given(timePeriodAssembler.fromRequest(A_TIME_PERIOD_REQUEST)).willReturn(timePeriodDto);
  }

  @Test
  public void whenCreatingFromDto_thenShouldCreatePassSaleDtoWithRightFields() {
    PassDto passDto = passSaleAssembler.fromRequest(A_PASS_SALE_REQUEST);

    assertThat(passDto.parkingZone).isEqualTo(A_PARKING_ZONE);
    assertThat(passDto.timePeriodDto).isEqualTo(timePeriodDto);
    assertThat(passDto.campusAccessCode).isEqualTo(AN_ACCESS_CODE);
    assertThat(passDto.deliveryDto).isEqualTo(A_DELIVERY_DTO);
  }

  @Test(expected = InvalidParkingZoneExceptionSale.class)
  public void givenInvalidParkingZone_whenCreatingFromDto_thenShouldThrowInvalidParkingZoneException() {
    A_PASS_SALE_REQUEST.parkingZone = "invalid";

    passSaleAssembler.fromRequest(A_PASS_SALE_REQUEST);
  }

  @Test(expected = InvalidCampusAccessCodeExceptionSale.class)
  public void givenInvalidCampusAccessCode_whenCreatingFromDto_thenShouldThrowInvalidCampusAccessCodeException() {
    A_PASS_SALE_REQUEST.campusAccessCode = "invalid";

    passSaleAssembler.fromRequest(A_PASS_SALE_REQUEST);
  }

  @Test(expected = InvalidPeriodArgumentException.class)
  public void givenAnInvalidPeriod_whenAssemblingFromRequest_shouldThrowInvalidPeriodException() {
    timePeriodDto.periodType = PeriodType.SINGLE_DAY;

    passSaleAssembler.fromRequest(A_PASS_SALE_REQUEST);
  }
}
