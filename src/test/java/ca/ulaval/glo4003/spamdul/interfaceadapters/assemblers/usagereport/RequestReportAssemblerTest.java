package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.dto.ReportCreationDto;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions.InvalidDateArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions.InvalidParkingZoneArgumentException;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class RequestReportAssemblerTest {

  private final LocalDate START_DATE = LocalDate.of(2020, 9, 12);
  private final String START_DATE_STRING = "2020-09-12";
  private final LocalDate END_DATE = LocalDate.of(2020, 9, 14);
  private final String END_DATE_STRING = "2020-09-14";
  private final ParkingZone PARKING_ZONE_3 = ParkingZone.ZONE_3;
  private final String PARKING_ZONE_3_STRING = "ZONE_3";

  private RequestReportAssembler requestReportAssembler;

  @Before
  public void setUp() {
    requestReportAssembler = new RequestReportAssembler();
  }

  @Test
  public void whenRequestingReportDto_shouldCreateReportDtoWithRightInfos() {
    ReportCreationDto dto = requestReportAssembler.fromDto(START_DATE_STRING, END_DATE_STRING, PARKING_ZONE_3_STRING);
    assertThat(dto.startDate).isEqualTo(START_DATE);
    assertThat(dto.endDate).isEqualTo(END_DATE);
    assertThat(dto.parkingZone).isEqualTo(PARKING_ZONE_3);
  }

  @Test(expected = InvalidDateArgumentException.class)
  public void givenAWrongStartDate_whenRequestingReportDto_shouldThrowIllegalArgumentException() {
    String startDate = "wrong date";
    ReportCreationDto dto = requestReportAssembler.fromDto(startDate, END_DATE_STRING, PARKING_ZONE_3_STRING);
  }

  @Test(expected = InvalidDateArgumentException.class)
  public void givenAWrongEndDate_whenRequestingReportDto_shouldThrowIllegalArgumentException() {
    String endDate = "wrong date";
    ReportCreationDto dto = requestReportAssembler.fromDto(START_DATE_STRING, endDate, PARKING_ZONE_3_STRING);
  }

  @Test(expected = InvalidParkingZoneArgumentException.class)
  public void givenAWrongParkingZone_whenRequestingReportDto_shouldThrowIllegalArgumentException() {
    String parkingZone = "wrong zone";
    ReportCreationDto dto = requestReportAssembler.fromDto(START_DATE_STRING, END_DATE_STRING, parkingZone);
  }
}
