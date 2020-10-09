package ca.ulaval.glo4003.spamdul.infrastructure.db.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFee;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFeeRepository;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import java.util.HashMap;
import java.util.Map;

public class HardCodedCampusAccessFeeRepository implements CampusAccessFeeRepository {

  private Map<Map<CarType, PeriodType>, CampusAccessFee> campusAccessFees;

  public HardCodedCampusAccessFeeRepository() {
    campusAccessFees = new HashMap<>();
    setGourmande();
    setEconomique();
    setHybrideEconomique();
    setSuperEconomique();
    setSansPollution();
  }

  public CampusAccessFee findBy(CarType carType, PeriodType PeriodType) {
    Map<CarType, PeriodType> key = new HashMap<>();
    key.put(carType, PeriodType);

    return campusAccessFees.get(key);
  }

  private void setSansPollution() {
    Map<CarType, PeriodType> singleSansPollution = new HashMap<>();
    Map<CarType, PeriodType> singleWeekSemesterSansPollution = new HashMap<>();
    Map<CarType, PeriodType> session1SansPollution = new HashMap<>();
    Map<CarType, PeriodType> session2SansPollution = new HashMap<>();
    Map<CarType, PeriodType> session3SansPollution = new HashMap<>();

    singleSansPollution.put(CarType.SANS_POLLUTION, PeriodType.SINGLE_DAY);
    campusAccessFees.put(singleSansPollution, new CampusAccessFee(0));

    singleWeekSemesterSansPollution.put(CarType.SANS_POLLUTION, PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER);
    campusAccessFees.put(singleWeekSemesterSansPollution, new CampusAccessFee(0));

    session1SansPollution.put(CarType.SANS_POLLUTION, PeriodType.ONE_SEMESTER);
    campusAccessFees.put(session1SansPollution, new CampusAccessFee(0));

    session2SansPollution.put(CarType.SANS_POLLUTION, PeriodType.TWO_SEMESTERS);
    campusAccessFees.put(session2SansPollution, new CampusAccessFee(0));

    session3SansPollution.put(CarType.SANS_POLLUTION, PeriodType.THREE_SEMESTERS);
    campusAccessFees.put(session3SansPollution, new CampusAccessFee(0));
  }

  private void setSuperEconomique() {
    Map<CarType, PeriodType> singleSuperEconomique = new HashMap<>();
    Map<CarType, PeriodType> singleWeekSemesterSuperEconomique = new HashMap<>();
    Map<CarType, PeriodType> session1SuperEconomique = new HashMap<>();
    Map<CarType, PeriodType> session2SuperEconomique = new HashMap<>();
    Map<CarType, PeriodType> session3SuperEconomique = new HashMap<>();

    singleSuperEconomique.put(CarType.SUPER_ECONOMIQUE, PeriodType.SINGLE_DAY);
    campusAccessFees.put(singleSuperEconomique, new CampusAccessFee(2));

    singleWeekSemesterSuperEconomique.put(CarType.SUPER_ECONOMIQUE, PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER);
    campusAccessFees.put(singleWeekSemesterSuperEconomique, new CampusAccessFee(5));

    session1SuperEconomique.put(CarType.SUPER_ECONOMIQUE, PeriodType.ONE_SEMESTER);
    campusAccessFees.put(session1SuperEconomique, new CampusAccessFee(20));

    session2SuperEconomique.put(CarType.SUPER_ECONOMIQUE, PeriodType.TWO_SEMESTERS);
    campusAccessFees.put(session2SuperEconomique, new CampusAccessFee(40));

    session3SuperEconomique.put(CarType.SUPER_ECONOMIQUE, PeriodType.THREE_SEMESTERS);
    campusAccessFees.put(session3SuperEconomique, new CampusAccessFee(50));
  }

  private void setHybrideEconomique() {
    Map<CarType, PeriodType> singleHybrideEconomique = new HashMap<>();
    Map<CarType, PeriodType> singleWeekSemesterHybridEconomique = new HashMap<>();
    Map<CarType, PeriodType> session1HybrideEconomique = new HashMap<>();
    Map<CarType, PeriodType> session2HybrideEconomique = new HashMap<>();
    Map<CarType, PeriodType> session3HybrideEconomique = new HashMap<>();

    singleHybrideEconomique.put(CarType.HYBRIDE_ECONOMIQUE, PeriodType.SINGLE_DAY);
    campusAccessFees.put(singleHybrideEconomique, new CampusAccessFee(4));

    singleWeekSemesterHybridEconomique.put(CarType.HYBRIDE_ECONOMIQUE, PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER);
    campusAccessFees.put(singleWeekSemesterHybridEconomique, new CampusAccessFee(15));

    session1HybrideEconomique.put(CarType.HYBRIDE_ECONOMIQUE, PeriodType.ONE_SEMESTER);
    campusAccessFees.put(session1HybrideEconomique, new CampusAccessFee(75));

    session2HybrideEconomique.put(CarType.HYBRIDE_ECONOMIQUE, PeriodType.TWO_SEMESTERS);
    campusAccessFees.put(session2HybrideEconomique, new CampusAccessFee(150));

    session3HybrideEconomique.put(CarType.HYBRIDE_ECONOMIQUE, PeriodType.THREE_SEMESTERS);
    campusAccessFees.put(session3HybrideEconomique, new CampusAccessFee(200));
  }

  private void setEconomique() {
    Map<CarType, PeriodType> singleEconomique = new HashMap<>();
    Map<CarType, PeriodType> singleWeekSemesterEconomique = new HashMap<>();
    Map<CarType, PeriodType> session1Economique = new HashMap<>();
    Map<CarType, PeriodType> session2Economique = new HashMap<>();
    Map<CarType, PeriodType> session3Economique = new HashMap<>();

    singleEconomique.put(CarType.ECONOMIQUE, PeriodType.SINGLE_DAY);
    campusAccessFees.put(singleEconomique, new CampusAccessFee(8));

    singleWeekSemesterEconomique.put(CarType.ECONOMIQUE, PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER);
    campusAccessFees.put(singleWeekSemesterEconomique, new CampusAccessFee(30));

    session1Economique.put(CarType.ECONOMIQUE, PeriodType.ONE_SEMESTER);
    campusAccessFees.put(session1Economique, new CampusAccessFee(120));

    session2Economique.put(CarType.ECONOMIQUE, PeriodType.TWO_SEMESTERS);
    campusAccessFees.put(session2Economique, new CampusAccessFee(240));

    session3Economique.put(CarType.ECONOMIQUE, PeriodType.THREE_SEMESTERS);
    campusAccessFees.put(session3Economique, new CampusAccessFee(300));
  }

  private void setGourmande() {
    Map<CarType, PeriodType> singleGourmande = new HashMap<>();
    singleGourmande.put(CarType.GOURMANDE, PeriodType.SINGLE_DAY);
    campusAccessFees.put(singleGourmande, new CampusAccessFee(12));

    Map<CarType, PeriodType> singleWeekSemesterGourmande = new HashMap<>();
    singleWeekSemesterGourmande.put(CarType.GOURMANDE, PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER);
    campusAccessFees.put(singleWeekSemesterGourmande, new CampusAccessFee(65));

    Map<CarType, PeriodType> session1Gourmande = new HashMap<>();
    session1Gourmande.put(CarType.GOURMANDE, PeriodType.ONE_SEMESTER);
    campusAccessFees.put(session1Gourmande, new CampusAccessFee(250));

    Map<CarType, PeriodType> session2Gourmande = new HashMap<>();
    session2Gourmande.put(CarType.GOURMANDE, PeriodType.TWO_SEMESTERS);
    campusAccessFees.put(session2Gourmande, new CampusAccessFee(500));

    Map<CarType, PeriodType> session3Gourmande = new HashMap<>();
    session3Gourmande.put(CarType.GOURMANDE, PeriodType.THREE_SEMESTERS);
    campusAccessFees.put(session3Gourmande, new CampusAccessFee(725));
  }


}
