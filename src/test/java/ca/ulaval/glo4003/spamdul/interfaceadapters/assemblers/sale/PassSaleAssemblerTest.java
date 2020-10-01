package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.PassType;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.dto.DeliveryRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.dto.PassSaleRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.DeliveryAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidCampusAccessCodeException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidParkingZoneException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidPassArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidPassTypeException;
import ca.ulaval.glo4003.spamdul.usecases.sale.DeliveryDto;
import ca.ulaval.glo4003.spamdul.usecases.sale.PassSaleDto;
import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class PassSaleAssemblerTest {

  private static final String A_PARKING_ZONE_STRING = "zone_1";
  private static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_1;
  private static final String A_PASS_TYPE_STRING = "monthly";
  private static final PassType A_PASS_TYPE = PassType.MONTHLY;
  private static final CampusAccessCode AN_ACCESS_CODE = new CampusAccessCode();
  private static final String AN_ACCESS_CODE_STRING = AN_ACCESS_CODE.toString();
  private static final DayOfWeek A_DAY_OF_THE_WEEK = DayOfWeek.SATURDAY;
  private static final String A_DAY_OF_WEEK_STRING = A_DAY_OF_THE_WEEK.toString();

  private PassSaleRequest A_PASS_SALE_REQUEST = new PassSaleRequest();
  private DeliveryRequest A_DELIVERY_REQUEST = new DeliveryRequest();
  private DeliveryDto A_DELIVERY_DTO = new DeliveryDto();
  private PassSaleAssembler passSaleAssembler;
  private DeliveryAssembler deliveryAssembler;

  @Before
  public void setUp() {
    A_PASS_SALE_REQUEST.parkingZone = A_PARKING_ZONE_STRING;
    A_PASS_SALE_REQUEST.passType = A_PASS_TYPE_STRING;
    A_PASS_SALE_REQUEST.campusAccessCode = AN_ACCESS_CODE_STRING;
    A_PASS_SALE_REQUEST.dayOfWeek = A_DAY_OF_WEEK_STRING;
    A_PASS_SALE_REQUEST.deliveryInfos = A_DELIVERY_REQUEST;

    deliveryAssembler = mock(DeliveryAssembler.class);
    passSaleAssembler = new PassSaleAssembler(deliveryAssembler);

    given(deliveryAssembler.fromDto(A_DELIVERY_REQUEST)).willReturn(A_DELIVERY_DTO);
  }

  @Test
  public void whenCreatingFromDto_thenShouldCreatePassSaleDtoWithRightFields() {
    PassSaleDto passSaleDto = passSaleAssembler.fromRequest(A_PASS_SALE_REQUEST);

    assertThat(passSaleDto.parkingZone).isEqualTo(A_PARKING_ZONE);
    assertThat(passSaleDto.passType).isEqualTo(A_PASS_TYPE);
    assertThat(passSaleDto.campusAccessCode).isEqualTo(AN_ACCESS_CODE);
    assertThat(passSaleDto.deliveryDto).isEqualTo(A_DELIVERY_DTO);
    assertThat(passSaleDto.dayOfWeek).isEqualTo(A_DAY_OF_THE_WEEK);
  }

  @Test(expected = InvalidParkingZoneException.class)
  public void givenInvalidParkingZone_whenCreatingFromDto_thenShouldThrowInvalidParkingZoneException() {
    A_PASS_SALE_REQUEST.parkingZone = "invalid";

    passSaleAssembler.fromRequest(A_PASS_SALE_REQUEST);
  }

  @Test(expected = InvalidPassTypeException.class)
  public void givenInvalidPassType_whenCreatingFromDto_thenShouldThrowInvalidInvalidPassTypeException() {
    A_PASS_SALE_REQUEST.passType = "invalid";

    passSaleAssembler.fromRequest(A_PASS_SALE_REQUEST);
  }

  @Test(expected = InvalidCampusAccessCodeException.class)
  public void givenInvalidCampusAccessCode_whenCreatingFromDto_thenShouldThrowInvalidCampusAccessCodeException() {
    A_PASS_SALE_REQUEST.campusAccessCode = "invalid";

    passSaleAssembler.fromRequest(A_PASS_SALE_REQUEST);
  }

  @Test(expected = InvalidPassArgumentException.class)
  public void givenInvalidDayOfWeek_whenCreatingFromDto_thenShouldThrowInvalidPassArgumentException() {
    A_PASS_SALE_REQUEST.dayOfWeek = "invalid";

    passSaleAssembler.fromRequest(A_PASS_SALE_REQUEST);
  }
}
