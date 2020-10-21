package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction;

import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionId;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.exceptions.EmptyPassCodeException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.exceptions.InvalidInfractionParkingZoneException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.exceptions.InvalidInfractionPassCodeFormatException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.exceptions.InvalidInfractionTimeOfTheDayException;
import ca.ulaval.glo4003.spamdul.usecases.infraction.InfractionValidationDto;
import com.google.common.truth.Truth;
import java.time.LocalTime;
import org.junit.Before;
import org.junit.Test;

public class InfractionAssemblerTest {

  public static final String A_PASS_CODE_STRING = "1";
  public static final PassCode A_PASS_CODE_ = PassCode.valueOf(A_PASS_CODE_STRING);
  public static final String A_PARKING_ZONE_STRING = "zone_1";
  public static final ParkingZone A_PARKING_ZONE = ParkingZone.valueOf(A_PARKING_ZONE_STRING.toUpperCase());
  public static final String A_TIME_OF_THE_DAY_STRING = "01:32";
  public static final LocalTime A_TIME_OF_THE_DAY = LocalTime.of(1, 32);
  public static final String AN_INFRACTION_DESCRIPTION = "a description";
  public static final String AN_INFRACTION_CODE_STRING = "INF-01";
  public static final InfractionCode AN_INFRACTION_CODE = InfractionCode.valueOf(AN_INFRACTION_CODE_STRING);
  public static final InfractionId A_INFRACTION_ID = new InfractionId();
  public static final int AN_AMOUNT = 99;

  private InfractionAssembler infractionAssembler;
  private InfractionRequest infractionRequest;

  @Before
  public void setUp() throws Exception {
    infractionAssembler = new InfractionAssembler();
    infractionRequest = new InfractionRequest();
    infractionRequest.passCode = A_PASS_CODE_STRING;
    infractionRequest.parkingZone = A_PARKING_ZONE_STRING;
    infractionRequest.timeOfTheDay = A_TIME_OF_THE_DAY_STRING;
  }

  @Test
  public void whenAssemblingFromRequest_shouldCreateDtoWithTheRightInfos() {
    InfractionValidationDto infractionValidationDto = infractionAssembler.fromRequest(infractionRequest);

    Truth.assertThat(infractionValidationDto.parkingZone).isEqualTo(A_PARKING_ZONE);
    Truth.assertThat(infractionValidationDto.passCode).isEqualTo(A_PASS_CODE_);
    Truth.assertThat(infractionValidationDto.time).isEqualTo(A_TIME_OF_THE_DAY);
  }

  @Test(expected = InvalidInfractionParkingZoneException.class)
  public void givenAnInvalidParkingZone_whenAssemblingFromRequest_shouldThrowInvalidInfractionParkingZoneException() {
    infractionRequest.parkingZone = "invalid";

    infractionAssembler.fromRequest(infractionRequest);
  }

  @Test(expected = EmptyPassCodeException.class)
  public void givenAnAbsentPassCode_whenAssemblingFromRequest_shouldThrowEmptyPassCodeException() {
    infractionRequest.passCode = "";

    infractionAssembler.fromRequest(infractionRequest);
  }

  @Test(expected = InvalidInfractionPassCodeFormatException.class)
  public void givenAnInvalidPassCode_whenAssemblingFromRequest_shouldThrowInvalidInfractionPassCodeFormatException() {
    infractionRequest.passCode = "invalid";

    infractionAssembler.fromRequest(infractionRequest);
  }

  @Test(expected = InvalidInfractionTimeOfTheDayException.class)
  public void givenAnInvalidTimeFormat_whenAssemblingFromRequest_shouldThrowInvalidInfractionTimeOfTheDayException() {
    infractionRequest.timeOfTheDay = "invalid";

    infractionAssembler.fromRequest(infractionRequest);
  }

  @Test
  public void givenAnInfraction_whenAssemblingResponse_shouldReturnResponseWithTheRightInfos() {
    Infraction infraction = new Infraction(A_INFRACTION_ID, AN_INFRACTION_DESCRIPTION, AN_INFRACTION_CODE, AN_AMOUNT);

    InfractionResponse response = infractionAssembler.toResponse(infraction);

    Truth.assertThat(response.amount).isEqualTo(AN_AMOUNT);
    Truth.assertThat(response.reason).isEqualTo(AN_INFRACTION_DESCRIPTION);
    Truth.assertThat(response.code).isEqualTo(AN_INFRACTION_CODE_STRING);
  }
}