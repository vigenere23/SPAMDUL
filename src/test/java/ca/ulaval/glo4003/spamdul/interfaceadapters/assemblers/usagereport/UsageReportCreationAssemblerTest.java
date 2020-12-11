package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingCategory;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions.InvalidDateArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions.InvalidParkingCategoryArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions.InvalidParkingZoneArgumentException;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportCreationDto;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class UsageReportCreationAssemblerTest {

  private final LocalDate START_DATE = LocalDate.of(2020, 9, 12);
  private final String START_DATE_STRING = "2020-09-12";
  private final LocalDate END_DATE = LocalDate.of(2020, 9, 14);
  private final String END_DATE_STRING = "2020-09-14";
  private final ParkingZone PARKING_ZONE_3 = ParkingZone.ZONE_3;
  private final String PARKING_ZONE_3_STRING = "ZONE_3";
  private final ParkingCategory A_PARKING_CATEGORY = ParkingCategory.CAR;
  private final String PARKING_CATEGORY_STRING = A_PARKING_CATEGORY.toString();

  private UsageReportCreationAssembler usageReportCreationAssembler;

  @Before
  public void setUp() {
    usageReportCreationAssembler = new UsageReportCreationAssembler();
  }

  @Test
  public void whenRequestingReportDto_shouldCreateReportDtoWithRightInfos() {
    UsageReportCreationDto dto = usageReportCreationAssembler.fromValues(START_DATE_STRING,
                                                                         END_DATE_STRING,
                                                                         PARKING_ZONE_3_STRING,
                                                                         PARKING_CATEGORY_STRING);
    assertThat(dto.startDate).isEqualTo(START_DATE);
    assertThat(dto.endDate).isEqualTo(END_DATE);
    assertThat(dto.parkingZone).isEqualTo(PARKING_ZONE_3);
    assertThat(dto.parkingCategory).isEqualTo(A_PARKING_CATEGORY);
  }

  @Test(expected = InvalidDateArgumentException.class)
  public void givenAWrongStartDate_whenRequestingReportDto_shouldThrowIllegalArgumentException() {
    String invalidStartDate = "wrong date";
    UsageReportCreationDto dto = usageReportCreationAssembler.fromValues(invalidStartDate,
                                                                         null,
                                                                         null,
                                                                         null);
  }

  @Test(expected = InvalidDateArgumentException.class)
  public void givenAWrongEndDate_whenRequestingReportDto_shouldThrowIllegalArgumentException() {
    String invalidEndDate = "wrong date";
    UsageReportCreationDto dto = usageReportCreationAssembler.fromValues(null,
                                                                         invalidEndDate,
                                                                         null,
                                                                         null);
  }

  @Test(expected = InvalidParkingZoneArgumentException.class)
  public void givenAWrongParkingZone_whenRequestingReportDto_shouldThrowIllegalArgumentException() {
    String invalidParkingZone = "wrong zone";
    UsageReportCreationDto dto = usageReportCreationAssembler.fromValues(null,
                                                                         null,
                                                                         invalidParkingZone,
                                                                         null);
  }

  @Test(expected = InvalidParkingCategoryArgumentException.class)
  public void givenAWrongParkingCategory_whenRequestingReportDto_shouldThrowIllegalArgumentException() {
    String invalidParkingCategory = "not a category";
    UsageReportCreationDto dto = usageReportCreationAssembler.fromValues(null,
                                                                         null,
                                                                         null,
                                                                         invalidParkingCategory);
  }
}
