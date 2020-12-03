package ca.ulaval.glo4003.spamdul.infrastructure.db.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccessFeeRepository;
import ca.ulaval.glo4003.spamdul.entity.user.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.utils.MapUtil;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.util.HashMap;
import java.util.Map;

public class HardCodedCampusAccessFeeRepository implements CampusAccessFeeRepository {

  private final Map<Map<CarType, PeriodType>, Amount> campusAccessFees;

  public HardCodedCampusAccessFeeRepository() {
    campusAccessFees = new HashMap<>();
    setGourmande();
    setEconomique();
    setHybrideEconomique();
    setSuperEconomique();
    setSansPollution();
  }

  @Override public Amount findBy(CarType carType, PeriodType PeriodType) {
    Map<CarType, PeriodType> key = new HashMap<>();
    key.put(carType, PeriodType);

    return campusAccessFees.get(key);
  }

  private void setSansPollution() {
    Map<CarType, PeriodType> singleSansPollution = MapUtil.toMap(CarType.SANS_POLLUTION, PeriodType.SINGLE_DAY);
    Map<CarType, PeriodType> singleWeekSemesterSansPollution = MapUtil.toMap(CarType.SANS_POLLUTION,
                                                                             PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER);
    Map<CarType, PeriodType> session1SansPollution = MapUtil.toMap(CarType.SANS_POLLUTION, PeriodType.ONE_SEMESTER);
    Map<CarType, PeriodType> session2SansPollution = MapUtil.toMap(CarType.SANS_POLLUTION, PeriodType.TWO_SEMESTERS);
    Map<CarType, PeriodType> session3SansPollution = MapUtil.toMap(CarType.SANS_POLLUTION, PeriodType.THREE_SEMESTERS);

    campusAccessFees.put(singleSansPollution, Amount.valueOf(0));
    campusAccessFees.put(singleWeekSemesterSansPollution, Amount.valueOf(0));
    campusAccessFees.put(session1SansPollution, Amount.valueOf(0));
    campusAccessFees.put(session2SansPollution, Amount.valueOf(0));
    campusAccessFees.put(session3SansPollution, Amount.valueOf(0));
  }

  private void setSuperEconomique() {
    Map<CarType, PeriodType> singleSuperEconomique = MapUtil.toMap(CarType.SUPER_ECONOMIQUE, PeriodType.SINGLE_DAY);
    Map<CarType, PeriodType> singleWeekSemesterSuperEconomique = MapUtil.toMap(CarType.SUPER_ECONOMIQUE,
                                                                               PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER);
    Map<CarType, PeriodType> session1SuperEconomique = MapUtil.toMap(CarType.SUPER_ECONOMIQUE, PeriodType.ONE_SEMESTER);
    Map<CarType, PeriodType> session2SuperEconomique = MapUtil.toMap(CarType.SUPER_ECONOMIQUE,
                                                                     PeriodType.TWO_SEMESTERS);
    Map<CarType, PeriodType> session3SuperEconomique = MapUtil.toMap(CarType.SUPER_ECONOMIQUE,
                                                                     PeriodType.THREE_SEMESTERS);

    campusAccessFees.put(singleSuperEconomique, Amount.valueOf(2));
    campusAccessFees.put(singleWeekSemesterSuperEconomique, Amount.valueOf(5));
    campusAccessFees.put(session1SuperEconomique, Amount.valueOf(20));
    campusAccessFees.put(session2SuperEconomique, Amount.valueOf(40));
    campusAccessFees.put(session3SuperEconomique, Amount.valueOf(50));
  }

  private void setHybrideEconomique() {
    Map<CarType, PeriodType> singleHybrideEconomique = MapUtil.toMap(CarType.HYBRIDE_ECONOMIQUE, PeriodType.SINGLE_DAY);
    Map<CarType, PeriodType> singleWeekSemesterHybridEconomique = MapUtil.toMap(CarType.HYBRIDE_ECONOMIQUE,
                                                                                PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER);
    Map<CarType, PeriodType> session1HybrideEconomique = MapUtil.toMap(CarType.HYBRIDE_ECONOMIQUE,
                                                                       PeriodType.ONE_SEMESTER);
    Map<CarType, PeriodType> session2HybrideEconomique = MapUtil.toMap(CarType.HYBRIDE_ECONOMIQUE,
                                                                       PeriodType.TWO_SEMESTERS);
    Map<CarType, PeriodType> session3HybrideEconomique = MapUtil.toMap(CarType.HYBRIDE_ECONOMIQUE,
                                                                       PeriodType.THREE_SEMESTERS);

    campusAccessFees.put(singleHybrideEconomique, Amount.valueOf(4));
    campusAccessFees.put(singleWeekSemesterHybridEconomique, Amount.valueOf(15));
    campusAccessFees.put(session1HybrideEconomique, Amount.valueOf(75));
    campusAccessFees.put(session2HybrideEconomique, Amount.valueOf(150));
    campusAccessFees.put(session3HybrideEconomique, Amount.valueOf(200));
  }

  private void setEconomique() {
    Map<CarType, PeriodType> singleEconomique = MapUtil.toMap(CarType.ECONOMIQUE, PeriodType.SINGLE_DAY);
    Map<CarType, PeriodType> singleWeekSemesterEconomique = MapUtil.toMap(CarType.ECONOMIQUE,
                                                                          PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER);
    Map<CarType, PeriodType> session1Economique = MapUtil.toMap(CarType.ECONOMIQUE, PeriodType.ONE_SEMESTER);
    Map<CarType, PeriodType> session2Economique = MapUtil.toMap(CarType.ECONOMIQUE, PeriodType.TWO_SEMESTERS);
    Map<CarType, PeriodType> session3Economique = MapUtil.toMap(CarType.ECONOMIQUE, PeriodType.THREE_SEMESTERS);

    campusAccessFees.put(singleEconomique, Amount.valueOf(8));
    campusAccessFees.put(singleWeekSemesterEconomique, Amount.valueOf(30));
    campusAccessFees.put(session1Economique, Amount.valueOf(120));
    campusAccessFees.put(session2Economique, Amount.valueOf(240));
    campusAccessFees.put(session3Economique, Amount.valueOf(300));
  }

  private void setGourmande() {
    Map<CarType, PeriodType> singleGourmande = MapUtil.toMap(CarType.GOURMANDE, PeriodType.SINGLE_DAY);
    Map<CarType, PeriodType> singleWeekSemesterGourmande = MapUtil.toMap(CarType.GOURMANDE,
                                                                         PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER);
    Map<CarType, PeriodType> session1Gourmande = MapUtil.toMap(CarType.GOURMANDE, PeriodType.ONE_SEMESTER);
    Map<CarType, PeriodType> session2Gourmande = MapUtil.toMap(CarType.GOURMANDE, PeriodType.TWO_SEMESTERS);
    Map<CarType, PeriodType> session3Gourmande = MapUtil.toMap(CarType.GOURMANDE, PeriodType.THREE_SEMESTERS);

    campusAccessFees.put(singleGourmande, Amount.valueOf(12));
    campusAccessFees.put(singleWeekSemesterGourmande, Amount.valueOf(65));
    campusAccessFees.put(session1Gourmande, Amount.valueOf(250));
    campusAccessFees.put(session2Gourmande, Amount.valueOf(500));
    campusAccessFees.put(session3Gourmande, Amount.valueOf(725));
  }


}
