package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.pass;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.PassType;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.dto.PassRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidParkingZoneException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidPassTypeException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidUserIdException;
import ca.ulaval.glo4003.spamdul.usecases.pass.PassDto;
import org.junit.Before;
import org.junit.Test;

public class PassAssemblerTest {

  private final String A_PARKING_ZONE_STRING = "zone_1";
  private final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_1;
  private final String A_PASS_TYPE_STRING = "monthly";
  private final PassType A_PASS_TYPE = PassType.MONTHLY;
  private final UserId A_USER_ID = new UserId();
  private final String A_USER_ID_STRING = A_USER_ID.toString();
  private final String A_INVALID_PARKING_ZONE = "test";
  private final String A_INVALID_PASS_TYPE = "test";
  private final String A_INVALID_USER_ID = "test";

  private PassRequest A_PASS_REQUEST = new PassRequest();
  private PassDto A_PASS_DTO = new PassDto();
  private PassAssembler passAssembler;

  @Before
  public void setUp() {
    A_PASS_REQUEST.userId = A_USER_ID_STRING;
    A_PASS_REQUEST.passType = A_PASS_TYPE_STRING;
    A_PASS_REQUEST.parkingZone = A_PARKING_ZONE_STRING;

    A_PASS_DTO.passType = A_PASS_TYPE;
    A_PASS_DTO.parkingZone = A_PARKING_ZONE;
    A_PASS_DTO.userId = A_USER_ID;

    passAssembler = new PassAssembler();
  }

  @Test
  public void whenCreatingFromDto_thenShouldReturnPassDtoWithRightFields() {
    PassDto passDto = passAssembler.fromDTO(A_PASS_REQUEST);

    assertThat(passDto.userId).isEqualTo(A_USER_ID);
    assertThat(passDto.parkingZone).isEqualTo(A_PARKING_ZONE);
    assertThat(passDto.passType).isEqualTo(A_PASS_TYPE);
  }

  @Test(expected = InvalidParkingZoneException.class)
  public void givenInvalidParkingZone_whenCreatingFromDto_thenShouldThrowInvalidParkingZoneException() {
    A_PASS_REQUEST.parkingZone = A_INVALID_PARKING_ZONE;

    passAssembler.fromDTO(A_PASS_REQUEST);
  }

  @Test(expected = InvalidPassTypeException.class)
  public void givenInvalidPassType_whenCreatingFromDto_thenShouldThrowInvalidInvalidPassTypeException() {
    A_PASS_REQUEST.passType = A_INVALID_PASS_TYPE;

    passAssembler.fromDTO(A_PASS_REQUEST);
  }

  @Test(expected = InvalidUserIdException.class)
  public void givenInvalidUserId_whenCreatingFromDto_thenShouldThrowInvalidUserIdException() {
    A_PASS_REQUEST.userId = A_INVALID_USER_ID;

    passAssembler.fromDTO(A_PASS_REQUEST);
  }

}
