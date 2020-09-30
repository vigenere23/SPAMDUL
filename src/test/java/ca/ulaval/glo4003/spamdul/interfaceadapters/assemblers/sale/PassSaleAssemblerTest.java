package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryMode;
import ca.ulaval.glo4003.spamdul.entity.delivery.post.PostalAddress;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.PassType;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.dto.DeliveryRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.dto.PassSaleRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.dto.PostalAddressRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.DeliveryAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidParkingZoneException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidPassTypeException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidUserIdException;
import ca.ulaval.glo4003.spamdul.usecases.sale.DeliveryDto;
import ca.ulaval.glo4003.spamdul.usecases.sale.PassSaleDto;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class PassSaleAssemblerTest {

  private final String A_DELIVER_MODE_STRING = "email";
  private final DeliveryMode A_DELIVERY_MODE = DeliveryMode.EMAIL;
  private final String A_EMAIL_ADDRESS = "test@test.ca";
  private final String A_POSTAL_ADDRESS_STRING = "test address";
  private final PostalAddress A_POSTAL_ADDRESS = new PostalAddress("", "", "", "", "", "");
  private final String A_PARKING_ZONE_STRING = "zone_1";
  private final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_1;
  private final String A_PASS_TYPE_STRING = "monthly";
  private final PassType A_PASS_TYPE = PassType.MONTHLY;
  private final UserId A_USER_ID = new UserId();
  private final String A_USER_ID_STRING = A_USER_ID.toString();

  private PassSaleRequest A_PASS_SALE_REQUEST = new PassSaleRequest();
  private DeliveryRequest A_DELIVERY_REQUEST = new DeliveryRequest();
  private DeliveryDto A_DELIVERY_DTO = new DeliveryDto();
  private PassSaleAssembler passSaleAssembler;
  private DeliveryAssembler deliveryAssembler;

  @Before
  public void setUp() {
    A_PASS_SALE_REQUEST.parkingZone = A_PARKING_ZONE_STRING;
    A_PASS_SALE_REQUEST.passType = A_PASS_TYPE_STRING;
    A_PASS_SALE_REQUEST.userId = A_USER_ID_STRING;

    A_DELIVERY_REQUEST.deliveryMode = A_DELIVER_MODE_STRING;
    A_DELIVERY_REQUEST.emailAddress = A_EMAIL_ADDRESS;
    A_DELIVERY_REQUEST.postalAddress = new PostalAddressRequest();
    A_PASS_SALE_REQUEST.deliveryInfos = A_DELIVERY_REQUEST;

    deliveryAssembler = mock(DeliveryAssembler.class);
    passSaleAssembler = new PassSaleAssembler(deliveryAssembler);

    given(deliveryAssembler.fromDto(A_DELIVERY_REQUEST)).willReturn(A_DELIVERY_DTO);
  }

  @Test
  public void whenCreatingFromDto_thenShouldCreatePassSaleDtoWithRightFields() {
    PassSaleDto passSaleDto = passSaleAssembler.fromDto(A_PASS_SALE_REQUEST);

    assertThat(passSaleDto.parkingZone).isEqualTo(A_PARKING_ZONE);
    assertThat(passSaleDto.passType).isEqualTo(A_PASS_TYPE);
    assertThat(passSaleDto.userId).isEqualTo(A_USER_ID);
    assertThat(passSaleDto.deliveryDto).isEqualTo(A_DELIVERY_DTO);
  }

  @Test(expected = InvalidParkingZoneException.class)
  public void givenInvalidParkingZone_whenCreatingFromDto_thenShouldThrowInvalidParkingZoneException() {
    A_PASS_SALE_REQUEST.parkingZone = "invalid";

    passSaleAssembler.fromDto(A_PASS_SALE_REQUEST);
  }

  @Test(expected = InvalidPassTypeException.class)
  public void givenInvalidPassType_whenCreatingFromDto_thenShouldThrowInvalidInvalidPassTypeException() {
    A_PASS_SALE_REQUEST.passType = "invalid";

    passSaleAssembler.fromDto(A_PASS_SALE_REQUEST);
  }

  @Test(expected = InvalidUserIdException.class)
  public void givenInvalidUserId_whenCreatingFromDto_thenShouldThrowInvalidUserIdException() {
    A_PASS_SALE_REQUEST.userId = "invalid";

    passSaleAssembler.fromDto(A_PASS_SALE_REQUEST);
  }

}
