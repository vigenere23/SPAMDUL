package ca.ulaval.glo4003.spamdul.infrastructure.db.campusaccess;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFee;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.Period;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import org.junit.Test;

public class HardCodedCampusAccessFeeRepositoryTest {

  @Test
  public void given1SessionGourmande_whenFindingFee_shouldReturnRightFee() {
    HardCodedCampusAccessFeeRepository repository = new HardCodedCampusAccessFeeRepository();

    CampusAccessFee feeBy = repository.findFeeBy(CarType.GOURMANDE, Period.SEMESTER_1);

    assertThat(feeBy.getFee()).isEqualTo(250);
  }

  @Test
  public void given2SessionEconomique_whenFindingFee_shouldReturnRightFee() {
    HardCodedCampusAccessFeeRepository repository = new HardCodedCampusAccessFeeRepository();

    CampusAccessFee feeBy = repository.findFeeBy(CarType.ECONOMIQUE, Period.SEMESTER_2);

    assertThat(feeBy.getFee()).isEqualTo(240);
  }

  @Test
  public void given3SessionSuperEconomique_whenFindingFee_shouldReturnRightFee() {
    HardCodedCampusAccessFeeRepository repository = new HardCodedCampusAccessFeeRepository();

    CampusAccessFee feeBy = repository.findFeeBy(CarType.SUPER_ECONOMIQUE, Period.SEMESTER_3);

    assertThat(feeBy.getFee()).isEqualTo(50);
  }

  @Test
  public void givenSingleSansPollution_whenFindingFee_shouldReturnRightFee() {
    HardCodedCampusAccessFeeRepository repository = new HardCodedCampusAccessFeeRepository();

    CampusAccessFee feeBy = repository.findFeeBy(CarType.SANS_POLLUTION, Period.SINGLE);

    assertThat(feeBy.getFee()).isEqualTo(0);
  }


}