package ca.ulaval.glo4003.spamdul.assemblers.infraction;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.assemblers.infraction.exceptions.InvalidInfractionParkingZoneException;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.usecases.infraction.dto.InfractionDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionId;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.ui.infractions.dto.InfractionRequest;
import ca.ulaval.glo4003.spamdul.ui.infractions.dto.InfractionResponse;
import ca.ulaval.glo4003.spamdul.shared.amount.Amount;
import org.junit.Before;
import org.junit.Test;

public class InfractionAssemblerTest {

  public static final String A_PASS_CODE_STRING = "1";
  public static final String A_PARKING_ZONE_STRING = "zone_1";
  public static final ParkingZone A_PARKING_ZONE = ParkingZone.valueOf(A_PARKING_ZONE_STRING.toUpperCase());
  public static final String AN_INFRACTION_DESCRIPTION = "a description";
  public static final String AN_INFRACTION_CODE_STRING = "INF-01";
  public static final InfractionCode AN_INFRACTION_CODE = InfractionCode.valueOf(AN_INFRACTION_CODE_STRING);
  public static final InfractionId A_INFRACTION_ID = InfractionId.valueOf("123");
  public static final Amount AN_AMOUNT = Amount.valueOf(99);

  private InfractionAssembler infractionAssembler;
  private InfractionRequest infractionRequest;

  @Before
  public void setUp() throws Exception {
    infractionAssembler = new InfractionAssembler();
    infractionRequest = new InfractionRequest();
    infractionRequest.passCode = A_PASS_CODE_STRING;
    infractionRequest.parkingZone = A_PARKING_ZONE_STRING;
  }

  @Test
  public void whenAssemblingFromRequest_shouldCreateDtoWithTheRightInfos() {
    PassToValidateDto passToValidateDto = infractionAssembler.fromRequest(infractionRequest);

    assertThat(passToValidateDto.parkingZone).isEqualTo(A_PARKING_ZONE);
    assertThat(passToValidateDto.passCode).isEqualTo(A_PASS_CODE_STRING);
  }

  @Test(expected = InvalidInfractionParkingZoneException.class)
  public void givenAnInvalidParkingZone_whenAssemblingFromRequest_shouldThrowInvalidInfractionParkingZoneException() {
    infractionRequest.parkingZone = "invalid";

    infractionAssembler.fromRequest(infractionRequest);
  }

  @Test
  public void givenAnInfraction_whenAssemblingResponse_shouldReturnResponseWithTheRightInfos() {
    InfractionDto infractionDto = new InfractionDto();
    infractionDto.id = A_INFRACTION_ID;
    infractionDto.amount = AN_AMOUNT;
    infractionDto.code = AN_INFRACTION_CODE;
    infractionDto.infractionDescription = AN_INFRACTION_DESCRIPTION;

    InfractionResponse response = infractionAssembler.toResponse(infractionDto);

    assertThat(response.amount).isEqualTo(AN_AMOUNT.asDouble());
    assertThat(response.reason).isEqualTo(AN_INFRACTION_DESCRIPTION);
    assertThat(response.code).isEqualTo(AN_INFRACTION_CODE_STRING);
  }

  @Test
  public void givenNull_whenAssemblingResponse_shouldReturnNull() {
    InfractionDto infractionDto = null;

    InfractionResponse response = infractionAssembler.toResponse(infractionDto);

    assertThat(response).isNull();
  }
}
