package ca.ulaval.glo4003.spamdul.infrastructure.db.campusaccess;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFee;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import org.junit.Test;

public class HardCodedCampusAccessFeeRepositoryTest {

  @Test
  public void given1SessionGourmande_whenFindingFee_shouldReturnRightFee() {
    HardCodedCampusAccessFeeRepository repository = new HardCodedCampusAccessFeeRepository();

    CampusAccessFee feeBy = repository.findBy(CarType.GOURMANDE, PeriodType.ONE_SEMESTER);

    assertThat(feeBy.getFee()).isEqualTo(250);
  }

  @Test
  public void given2SessionEconomique_whenFindingFee_shouldReturnRightFee() {
    HardCodedCampusAccessFeeRepository repository = new HardCodedCampusAccessFeeRepository();

    CampusAccessFee feeBy = repository.findBy(CarType.ECONOMIQUE, PeriodType.TWO_SEMESTERS);

    assertThat(feeBy.getFee()).isEqualTo(240);
  }

  @Test
  public void given3SessionSuperEconomique_whenFindingFee_shouldReturnRightFee() {
    HardCodedCampusAccessFeeRepository repository = new HardCodedCampusAccessFeeRepository();

    CampusAccessFee feeBy = repository.findBy(CarType.SUPER_ECONOMIQUE, PeriodType.THREE_SEMESTERS);

    assertThat(feeBy.getFee()).isEqualTo(50);
  }

  @Test
  public void givenSingleSansPollution_whenFindingFee_shouldReturnRightFee() {
    HardCodedCampusAccessFeeRepository repository = new HardCodedCampusAccessFeeRepository();

    CampusAccessFee feeBy = repository.findBy(CarType.SANS_POLLUTION, PeriodType.SINGLE_DAY);

    assertThat(feeBy.getFee()).isEqualTo(0);
  }


}