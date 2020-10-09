package ca.ulaval.glo4003.spamdul.infrastructure.db.campusaccess;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFee;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.infrastructure.reader.CsvReader;
import com.google.common.truth.Truth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class CampusAccessFeeCsvRepositoryTest {

  private CampusAccessFeeCsvRepository repository;
  private CsvReader csvReader;

  @Before
  public void setUp() throws Exception {
    csvReader = mock(CsvReader.class);
    repository = new CampusAccessFeeCsvRepository(csvReader);
    given(csvReader.read(anyString())).willReturn(generateReadCsv());
  }

  private List<List<String>> generateReadCsv() {
    List<List<String>> lines = new ArrayList<>();
    lines.add(Arrays.asList("Listype\temps", "3 session", "1h", "1 session", "1j", "1j/semaine/session", "2 session"));
    lines.add(Arrays.asList("Super économique", "50", "1", "20", "2", "5", "40"));
    lines.add(Arrays.asList("Hybride économique", "200", "1.5", "75", "4", "15", "150"));
    lines.add(Arrays.asList("Gourmande", "725", "3", "250", "12", "65", "500"));
    lines.add(Arrays.asList("0 pollution", "0", "0", "0", "0", "0", "0"));
    lines.add(Arrays.asList("économique", "300", "2", "120", "8", "30", "240"));

    return lines;
  }

  @Test
  public void givenGourmandeAndOneHour_whenFindingBy_shouldReturnTheRightCampusAccessFee() {
    CampusAccessFee fee = repository.findBy(CarType.GOURMANDE, PeriodType.ONE_HOUR);

    Truth.assertThat(fee.getFee()).isEqualTo(3);
  }

  @Test
  public void givenEconomiqueAndOneOneDayPerWeekPerSemester_whenFindingBy_shouldReturnTheRightCampusAccessFee() {
    CampusAccessFee fee = repository.findBy(CarType.ECONOMIQUE, PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER);

    Truth.assertThat(fee.getFee()).isEqualTo(30);
  }

  @Test
  public void givenHybrideEconomiqueAndOneOneSemester_whenFindingBy_shouldReturnTheRightCampusAccessFee() {
    CampusAccessFee fee = repository.findBy(CarType.HYBRIDE_ECONOMIQUE, PeriodType.ONE_SEMESTER);

    Truth.assertThat(fee.getFee()).isEqualTo(75);
  }

  @Test
  public void givenSuperEconomiqueAndTwoSemester_whenFindingBy_shouldReturnTheRightCampusAccessFee() {
    CampusAccessFee fee = repository.findBy(CarType.SUPER_ECONOMIQUE, PeriodType.TWO_SEMESTERS);

    Truth.assertThat(fee.getFee()).isEqualTo(40);
  }

  @Test
  public void givenZeroPollutionAndThreeSemester_whenFindingBy_shouldReturnTheRightCampusAccessFee() {
    CampusAccessFee fee = repository.findBy(CarType.SANS_POLLUTION, PeriodType.THREE_SEMESTERS);

    Truth.assertThat(fee.getFee()).isEqualTo(0);
  }
}