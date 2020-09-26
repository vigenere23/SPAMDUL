package ca.ulaval.glo4003.spamdul.infrastructure.db.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFee;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFeeRepository;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.Period;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import java.util.HashMap;
import java.util.Map;

public class HardCodedCampusAccessFeeRepository implements CampusAccessFeeRepository {

  private Map<Map<CarType, Period>, CampusAccessFee> campusAccessFees;

  public HardCodedCampusAccessFeeRepository() {
    campusAccessFees = new HashMap<>();
    setGourmande();
    setEconomique();
    setHybrideEconomique();
    setSuperEconomique();
    setSansPollution();
  }

  public CampusAccessFee findFeeBy(CarType carType, Period period) {
    Map<CarType, Period> key = new HashMap<>();
    key.put(carType, period);

    return campusAccessFees.get(key);
  }

  private void setSansPollution() {
    Map<CarType, Period> singleSansPollution = new HashMap<>();
    Map<CarType, Period> singleWeekSemesterSansPollution = new HashMap<>();
    Map<CarType, Period> session1SansPollution = new HashMap<>();
    Map<CarType, Period> session2SansPollution = new HashMap<>();
    Map<CarType, Period> session3SansPollution = new HashMap<>();

    singleSansPollution.put(CarType.SANS_POLLUTION, Period.SINGLE);
    campusAccessFees.put(singleSansPollution, new CampusAccessFee(0));

    singleWeekSemesterSansPollution.put(CarType.SANS_POLLUTION, Period.SINGLE_DAY_PER_WEEK_PER_SEMESTER);
    campusAccessFees.put(singleWeekSemesterSansPollution, new CampusAccessFee(0));

    session1SansPollution.put(CarType.SANS_POLLUTION, Period.SEMESTER_1);
    campusAccessFees.put(session1SansPollution, new CampusAccessFee(0));

    session2SansPollution.put(CarType.SANS_POLLUTION, Period.SEMESTER_2);
    campusAccessFees.put(session2SansPollution, new CampusAccessFee(0));

    session3SansPollution.put(CarType.SANS_POLLUTION, Period.SEMESTER_3);
    campusAccessFees.put(session3SansPollution, new CampusAccessFee(0));
  }

  private void setSuperEconomique() {
    Map<CarType, Period> singleSuperEconomique = new HashMap<>();
    Map<CarType, Period> singleWeekSemesterSuperEconomique = new HashMap<>();
    Map<CarType, Period> session1SuperEconomique = new HashMap<>();
    Map<CarType, Period> session2SuperEconomique = new HashMap<>();
    Map<CarType, Period> session3SuperEconomique = new HashMap<>();

    singleSuperEconomique.put(CarType.SUPER_ECONOMIQUE, Period.SINGLE);
    campusAccessFees.put(singleSuperEconomique, new CampusAccessFee(2));

    singleWeekSemesterSuperEconomique.put(CarType.SUPER_ECONOMIQUE, Period.SINGLE_DAY_PER_WEEK_PER_SEMESTER);
    campusAccessFees.put(singleWeekSemesterSuperEconomique, new CampusAccessFee(5));

    session1SuperEconomique.put(CarType.SUPER_ECONOMIQUE, Period.SEMESTER_1);
    campusAccessFees.put(session1SuperEconomique, new CampusAccessFee(20));

    session2SuperEconomique.put(CarType.SUPER_ECONOMIQUE, Period.SEMESTER_2);
    campusAccessFees.put(session2SuperEconomique, new CampusAccessFee(40));

    session3SuperEconomique.put(CarType.SUPER_ECONOMIQUE, Period.SEMESTER_3);
    campusAccessFees.put(session3SuperEconomique, new CampusAccessFee(50));
  }

  private void setHybrideEconomique() {
    Map<CarType, Period> singleHybrideEconomique = new HashMap<>();
    Map<CarType, Period> singleWeekSemesterHybridEconomique = new HashMap<>();
    Map<CarType, Period> session1HybrideEconomique = new HashMap<>();
    Map<CarType, Period> session2HybrideEconomique = new HashMap<>();
    Map<CarType, Period> session3HybrideEconomique = new HashMap<>();

    singleHybrideEconomique.put(CarType.HYBRIDE_ECONOMIQUE, Period.SINGLE);
    campusAccessFees.put(singleHybrideEconomique, new CampusAccessFee(4));

    singleWeekSemesterHybridEconomique.put(CarType.HYBRIDE_ECONOMIQUE, Period.SINGLE_DAY_PER_WEEK_PER_SEMESTER);
    campusAccessFees.put(singleWeekSemesterHybridEconomique, new CampusAccessFee(15));

    session1HybrideEconomique.put(CarType.HYBRIDE_ECONOMIQUE, Period.SEMESTER_1);
    campusAccessFees.put(session1HybrideEconomique, new CampusAccessFee(75));

    session2HybrideEconomique.put(CarType.HYBRIDE_ECONOMIQUE, Period.SEMESTER_2);
    campusAccessFees.put(session2HybrideEconomique, new CampusAccessFee(150));

    session3HybrideEconomique.put(CarType.HYBRIDE_ECONOMIQUE, Period.SEMESTER_3);
    campusAccessFees.put(session3HybrideEconomique, new CampusAccessFee(200));
  }

  private void setEconomique() {
    Map<CarType, Period> singleEconomique = new HashMap<>();
    Map<CarType, Period> singleWeekSemesterEconomique = new HashMap<>();
    Map<CarType, Period> session1Economique = new HashMap<>();
    Map<CarType, Period> session2Economique = new HashMap<>();
    Map<CarType, Period> session3Economique = new HashMap<>();

    singleEconomique.put(CarType.ECONOMIQUE, Period.SINGLE);
    campusAccessFees.put(singleEconomique, new CampusAccessFee(8));

    singleWeekSemesterEconomique.put(CarType.ECONOMIQUE, Period.SINGLE_DAY_PER_WEEK_PER_SEMESTER);
    campusAccessFees.put(singleWeekSemesterEconomique, new CampusAccessFee(30));

    session1Economique.put(CarType.ECONOMIQUE, Period.SEMESTER_1);
    campusAccessFees.put(session1Economique, new CampusAccessFee(120));

    session2Economique.put(CarType.ECONOMIQUE, Period.SEMESTER_2);
    campusAccessFees.put(session2Economique, new CampusAccessFee(240));

    session3Economique.put(CarType.ECONOMIQUE, Period.SEMESTER_3);
    campusAccessFees.put(session3Economique, new CampusAccessFee(300));
  }

  private void setGourmande() {
    Map<CarType, Period> singleGourmande = new HashMap<>();
    singleGourmande.put(CarType.GOURMANDE, Period.SINGLE);
    campusAccessFees.put(singleGourmande, new CampusAccessFee(12));

    Map<CarType, Period> singleWeekSemesterGourmande = new HashMap<>();
    singleWeekSemesterGourmande.put(CarType.GOURMANDE, Period.SINGLE_DAY_PER_WEEK_PER_SEMESTER);
    campusAccessFees.put(singleWeekSemesterGourmande, new CampusAccessFee(65));

    Map<CarType, Period> session1Gourmande = new HashMap<>();
    session1Gourmande.put(CarType.GOURMANDE, Period.SEMESTER_1);
    campusAccessFees.put(session1Gourmande, new CampusAccessFee(250));

    Map<CarType, Period> session2Gourmande = new HashMap<>();
    session2Gourmande.put(CarType.GOURMANDE, Period.SEMESTER_2);
    campusAccessFees.put(session2Gourmande, new CampusAccessFee(500));

    Map<CarType, Period> session3Gourmande = new HashMap<>();
    session3Gourmande.put(CarType.GOURMANDE, Period.SEMESTER_3);
    campusAccessFees.put(session3Gourmande, new CampusAccessFee(725));
  }


}
