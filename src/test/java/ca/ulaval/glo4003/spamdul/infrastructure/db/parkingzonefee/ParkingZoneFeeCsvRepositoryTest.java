package ca.ulaval.glo4003.spamdul.infrastructure.db.parkingzonefee;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZoneFee;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.infrastructure.reader.CsvReader;
import com.google.common.truth.Truth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ParkingZoneFeeCsvRepositoryTest {

  private ParkingZoneFeeCsvRepository repository;
  private CsvReader reader;

  @Before
  public void setUp() throws Exception {
    reader = mock(CsvReader.class);
    repository = new ParkingZoneFeeCsvRepository(reader);
    given(reader.read(anyString())).willReturn(generateReadCsv());
  }

  private List<List<String>> generateReadCsv() {
    List<List<String>> lines = new ArrayList<>();
    lines.add(Arrays.asList("zone\temps", "Mensuel", "1j/semaine/session", "3 session", "1 session", "2 session"));
    lines.add(Arrays.asList("Zone2", "121", "163", "872", "366", "604"));
    lines.add(Arrays.asList("Zone1", "183", "163", "1239", "544", "895"));
    lines.add(Arrays.asList("ZoneR", "121", "163", "872", "366", "604"));
    lines.add(Arrays.asList("Zone3", "92", "163", "620", "272", "447"));

    return lines;
  }

  @Test
  public void givenZone2Mensuel_whenFindingBy_shouldReturnRightFee() {
    ParkingZoneFee parkingZoneFee = repository.findBy(ParkingZone.ZONE_2, PeriodType.MONTHLY);

    Truth.assertThat(parkingZoneFee.getFee()).isEqualTo(121);
  }

  @Test
  public void givenZone1OneDayPerWeekPerSemester_whenFindingBy_shouldReturnRighFee() {
    ParkingZoneFee parkingZoneFee = repository.findBy(ParkingZone.ZONE_1, PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER);

    Truth.assertThat(parkingZoneFee.getFee()).isEqualTo(163);
  }

  @Test
  public void givenZoneR3Session_whenFindingBy_shouldReturnRighFee() {
    ParkingZoneFee parkingZoneFee = repository.findBy(ParkingZone.ZONE_R, PeriodType.THREE_SEMESTERS);

    Truth.assertThat(parkingZoneFee.getFee()).isEqualTo(872);
  }

  @Test
  public void givenZone31ession_whenFindingBy_shouldReturnRighFee() {
    ParkingZoneFee parkingZoneFee = repository.findBy(ParkingZone.ZONE_3, PeriodType.ONE_SEMESTER);

    Truth.assertThat(parkingZoneFee.getFee()).isEqualTo(272);
  }

  @Test
  public void givenZone12Session_whenFindingBy_shouldReturnRighFee() {
    ParkingZoneFee parkingZoneFee = repository.findBy(ParkingZone.ZONE_1, PeriodType.TWO_SEMESTERS);

    Truth.assertThat(parkingZoneFee.getFee()).isEqualTo(895);
  }

  @Test(expected = CantFindParkingZoneFeeException.class)
  public void givenAnInvalidParkingZone_whenFindingBy_shouldThrowCantFindParkingZoneFeeException() {
    List<List<String>> lists = generateReadCsv();
    lists.remove(1); //Removing Zone2
    given(reader.read(anyString())).willReturn(lists);

    repository.findBy(ParkingZone.ZONE_2, PeriodType.MONTHLY);
  }

  @Test(expected = CantFindParkingZoneFeeException.class)
  public void givenAnInvalidPeriodType_whenFindingBy_shouldThrowCantFindParkingZoneException() {
    repository.findBy(ParkingZone.ZONE_1, PeriodType.SINGLE_DAY);
  }
}