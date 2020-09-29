package ca.ulaval.glo4003.spamdul.entity.usagereport;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogAgglomerator;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingZone;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

  private Map<LocalDate, List<ParkingAccessLog>> ACCESSES_PER_DAY = new HashMap<>();
  List<ParkingAccessLog> parkingAccessLogs = new ArrayList<>();

  private final LocalDate DATE = LocalDate.of(2011, 1, 1);
  private final Integer NUMBER_OF_ACCESS = 1;
  private final ParkingZone PARKING_ZONE = ParkingZone.ZONE_1;
  private final ParkingAccessLog PARKING_ACCESS_LOG = mock(ParkingAccessLog.class);

  @Before
  public void setUp() {
    usageReportFactory = new UsageReportFactory();
    parkingAccessLogs.add(PARKING_ACCESS_LOG);
    ACCESSES_PER_DAY.put(DATE, parkingAccessLogs);
  }

  @Test
  public void whenCreatingAUsageReport_ShouldCreateUsageReport() {
    UsageReport usageReport = usageReportFactory.create(ACCESSES_PER_DAY);
    assertThat(usageReport.getUsageReport().get(DATE)).isEqualTo(NUMBER_OF_ACCESS);
  }

  @Test
  public void whenCreatingAUsageReportWithZone_ShouldCreateUsageReport() {
    UsageReport usageReport = usageReportFactory.create(ACCESSES_PER_DAY, PARKING_ZONE);
    assertThat(usageReport.getUsageReport().get(DATE)).isEqualTo(NUMBER_OF_ACCESS);
    assertThat(usageReport.getParkingZones()).isEqualTo(PARKING_ZONE);
  }

}
