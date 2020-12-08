package ca.ulaval.glo4003.spamdul.infrastructure.db.parking.campusaccess;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.user.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import org.junit.Test;

public class HardCodedCampusAccessFeeRepositoryTest {

  @Test
  public void given1SessionGourmande_whenFindingFee_shouldReturnRightFee() {
    HardCodedCampusAccessFeeRepository repository = new HardCodedCampusAccessFeeRepository();

    Amount fee = repository.findBy(CarType.GOURMANDE, PeriodType.ONE_SEMESTER);

    assertThat(fee).isEqualTo(Amount.valueOf(250));
  }

  @Test
  public void given2SessionEconomique_whenFindingFee_shouldReturnRightFee() {
    HardCodedCampusAccessFeeRepository repository = new HardCodedCampusAccessFeeRepository();

    Amount fee = repository.findBy(CarType.ECONOMIQUE, PeriodType.TWO_SEMESTERS);

    assertThat(fee).isEqualTo(Amount.valueOf(240));
  }

  @Test
  public void given3SessionSuperEconomique_whenFindingFee_shouldReturnRightFee() {
    HardCodedCampusAccessFeeRepository repository = new HardCodedCampusAccessFeeRepository();

    Amount fee = repository.findBy(CarType.SUPER_ECONOMIQUE, PeriodType.THREE_SEMESTERS);

    assertThat(fee).isEqualTo(Amount.valueOf(50));
  }

  @Test
  public void givenSingleSansPollution_whenFindingFee_shouldReturnRightFee() {
    HardCodedCampusAccessFeeRepository repository = new HardCodedCampusAccessFeeRepository();

    Amount fee = repository.findBy(CarType.SANS_POLLUTION, PeriodType.SINGLE_DAY);

    assertThat(fee).isEqualTo(Amount.valueOf(0));
  }


}
