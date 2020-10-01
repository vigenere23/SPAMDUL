package ca.ulaval.glo4003.spamdul.entity.usagereport;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogId;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UsageReportFactoryTest {

  private UsageReportFactory usageReportFactory;
  List<ParkingAccessLog> parkingAccessLogs;
  List<ParkingAccessLog> anotherParkingAccessLogs;

  private final Map<LocalDate, List<ParkingAccessLog>> ACCESSES_PER_DAY = new HashMap<>();

  private final LocalDate A_DATE = LocalDate.of(2011, 1, 1);
  private final LocalDate ANOTHER_DATE = LocalDate.of(2011, 1, 2);
  private final Integer NUMBER_OF_ACCESS = 1;
  private final Integer ANOTHER_NUMBER_OF_ACCESS = 2;
  private final Integer TOTAL_NUMBER_OF_ACCESS = 3;
  private final ParkingZone PARKING_ZONE_1 = ParkingZone.ZONE_1;
  private final ParkingZone PARKING_ZONE_2 = ParkingZone.ZONE_2;
  private final ParkingAccessLog PARKING_ACCESS_LOG_ZONE_1 = new ParkingAccessLog(new ParkingAccessLogId(),
                                                                                  PARKING_ZONE_1,
                                                                                  A_DATE);
  private final ParkingAccessLog PARKING_ACCESS_LOG_ZONE_2 = new ParkingAccessLog(new ParkingAccessLogId(),
                                                                                  PARKING_ZONE_2,
                                                                                  ANOTHER_DATE);

  @Before
  public void setUp() {
    usageReportFactory = new UsageReportFactory();
    parkingAccessLogs = new ArrayList<>();
    anotherParkingAccessLogs = new ArrayList<>();
    parkingAccessLogs.add(PARKING_ACCESS_LOG_ZONE_1);
    anotherParkingAccessLogs.add(PARKING_ACCESS_LOG_ZONE_2);
    anotherParkingAccessLogs.add(PARKING_ACCESS_LOG_ZONE_2);
    ACCESSES_PER_DAY.put(A_DATE, parkingAccessLogs);
    ACCESSES_PER_DAY.put(ANOTHER_DATE, anotherParkingAccessLogs);
  }

  @Test
  public void whenCreatingAUsageReportWithoutAZone_ShouldCreateUsageReport() {
    UsageReport usageReport = usageReportFactory.create(ACCESSES_PER_DAY);
    assertThat(usageReport.getUsageReport().get(A_DATE)).isEqualTo(NUMBER_OF_ACCESS);
    assertThat(usageReport.getUsageReport().get(ANOTHER_DATE)).isEqualTo(ANOTHER_NUMBER_OF_ACCESS);
    assertThat(usageReport.getParkingZone()).isNull();
    assertThat(usageReport.getTotalOfEntry()).isEqualTo(TOTAL_NUMBER_OF_ACCESS);
  }

  @Test
  public void whenCreatingAUsageReportWithZone_ShouldCreateUsageReportWithZone() {
    UsageReport usageReport = usageReportFactory.create(ACCESSES_PER_DAY, PARKING_ZONE_1);
    assertThat(usageReport.getUsageReport().get(A_DATE)).isEqualTo(NUMBER_OF_ACCESS);
    assertThat(usageReport.getUsageReport().get(ANOTHER_DATE)).isEqualTo(ANOTHER_NUMBER_OF_ACCESS);
    assertThat(usageReport.getParkingZone()).isEqualTo(PARKING_ZONE_1);
    assertThat(usageReport.getTotalOfEntry()).isEqualTo(TOTAL_NUMBER_OF_ACCESS);
  }
}
