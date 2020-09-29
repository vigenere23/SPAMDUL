package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingZone;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.dto.ReportCreationDto;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.dto.ReportRequest;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions.InvalidDateArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions.InvalidParkingZoneArgumentException;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class RequestReportAssemblerTest {

  private final LocalDate START_DATE = LocalDate.of(2020, 9, 12);
  private final String START_DATE_STRING = "2020-09-12";
  private final LocalDate END_DATE = LocalDate.of(2020, 9, 14);
  private final String END_DATE_STRING = "2020-09-14";
  private final ParkingZone PARKING_ZONE_3 = ParkingZone.ZONE_3;
  private final String PARKING_ZONE_3_STRING = "ZONE_3";

  private RequestReportAssembler requestReportAssembler;
  private ReportCreationDto reportCreationDto;
  private ReportRequest reportRequest;

  @Before
  public void setUp() {
    requestReportAssembler = new RequestReportAssembler();
    reportCreationDto = new ReportCreationDto();
    reportCreationDto.startDate = START_DATE;
    reportCreationDto.endDate = END_DATE;
    reportCreationDto.parkingZone = PARKING_ZONE_3;
    reportRequest = new ReportRequest();
    reportRequest.startDate = START_DATE_STRING;
    reportRequest.endDate = END_DATE_STRING;
    reportRequest.parkingZone = PARKING_ZONE_3_STRING;
  }

  @Test
  public void whenRequestingReportDto_shouldCreateReportDtoWithRightInfos() {
    ReportCreationDto dto = requestReportAssembler.fromDto(reportRequest);
    assertThat(dto.startDate).isEqualTo(START_DATE);
    assertThat(dto.endDate).isEqualTo(END_DATE);
    assertThat(dto.parkingZone).isEqualTo(PARKING_ZONE_3);
  }

  @Test(expected = InvalidDateArgumentException.class)
  public void givenAWrongStartDate_whenRequestingReportDto_shouldThrowIllegalArgumentException() {
    reportRequest.startDate = "wrong date";
    ReportCreationDto dto = requestReportAssembler.fromDto(reportRequest);
  }

  @Test(expected = InvalidDateArgumentException.class)
  public void givenAWrongEndDate_whenRequestingReportDto_shouldThrowIllegalArgumentException() {
    reportRequest.endDate = "wrong date";
    ReportCreationDto dto = requestReportAssembler.fromDto(reportRequest);
  }

  @Test(expected = InvalidParkingZoneArgumentException.class)
  public void givenAWrongParkingZone_whenRequestingReportDto_shouldThrowIllegalArgumentException() {
    reportRequest.parkingZone = "wrong zone";
    ReportCreationDto dto = requestReportAssembler.fromDto(reportRequest);
  }
}
