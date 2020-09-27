package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.PassType;
import ca.ulaval.glo4003.spamdul.entity.post.PostalAddress;
import ca.ulaval.glo4003.spamdul.entity.sale.DeliveryMode;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.dto.PassRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.dto.SaleRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.pass.PassAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidDeliveryModeException;
import ca.ulaval.glo4003.spamdul.usecases.pass.PassDto;
import ca.ulaval.glo4003.spamdul.usecases.sale.SaleDto;
import org.junit.Before;
import org.junit.Test;

public class SaleAssemblerTest {

  private final String A_DELIVER_MODE_STRING = "email";
  private final DeliveryMode A_DELIVERY_MODE = DeliveryMode.EMAIL;
  private final String A_EMAIL_ADDRESS = "test@test.ca";
  private final String A_POSTAL_ADDRESS_STRING = "test address";
  private final PostalAddress A_POSTAL_ADDRESS = new PostalAddress(A_POSTAL_ADDRESS_STRING);
  private final String A_PARKING_ZONE_STRING = "zone_1";
  private final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_1;
  private final String A_PASS_TYPE_STRING = "monthly";
  private final PassType A_PASS_TYPE = PassType.MONTHLY;
  private final UserId A_USER_ID = new UserId();
  private final String A_USER_ID_STRING = A_USER_ID.toString();

  private SaleRequest A_SALE_REQUEST = new SaleRequest();
  private PassRequest A_PASS_REQUEST = new PassRequest();
  private PassDto A_PASS_DTO = new PassDto();
  private PassAssembler passAssembler;
  private SaleAssembler saleAssembler;

  @Before
  public void setUp() {
    A_PASS_REQUEST.parkingZone = A_PARKING_ZONE_STRING;
    A_PASS_REQUEST.passType = A_PASS_TYPE_STRING;
    A_PASS_REQUEST.userId = A_USER_ID_STRING;

    A_SALE_REQUEST.deliveryMode = A_DELIVER_MODE_STRING;
    A_SALE_REQUEST.emailAddress = A_EMAIL_ADDRESS;
    A_SALE_REQUEST.postalAddress = A_POSTAL_ADDRESS_STRING;
    A_SALE_REQUEST.pass = A_PASS_REQUEST;

    A_PASS_DTO.passType = A_PASS_TYPE;
    A_PASS_DTO.parkingZone = A_PARKING_ZONE;
    A_PASS_DTO.userId = A_USER_ID;

    passAssembler = mock(PassAssembler.class);
    saleAssembler = new SaleAssembler(passAssembler);

    given(passAssembler.fromDTO(A_PASS_REQUEST)).willReturn(A_PASS_DTO);
  }

  @Test
  public void whenCreatingFromDto_thenShouldCreateSaleDtoWithRightFields() {
    SaleDto saleDto = saleAssembler.fromDto(A_SALE_REQUEST);

    assertThat(saleDto.deliveryMode).isEqualTo(A_DELIVERY_MODE);
    assertThat(saleDto.emailAddress).isEqualTo(A_EMAIL_ADDRESS);
    assertThat(saleDto.postalAddress.getAddress()).isEqualTo(A_POSTAL_ADDRESS.getAddress());
    assertThat(saleDto.passDTO).isEqualTo(A_PASS_DTO);
  }

  @Test(expected = InvalidDeliveryModeException.class)
  public void givenInvalidDeliveryMode_whenCreatingFromDto_thenShouldThrowInvalidDeliveryModeException() {
    A_SALE_REQUEST.deliveryMode = "test";

    saleAssembler.fromDto(A_SALE_REQUEST);
  }
}
