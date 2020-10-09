package ca.ulaval.glo4003.spamdul.infrastructure.db.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFee;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFeeRepository;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.infrastructure.reader.CsvReader;
import java.text.Collator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CampusAccessFeeCsvRepository implements CampusAccessFeeRepository {

  private final String CSV_PATH = "frais-acces.csv";
  private CsvReader reader;

  public CampusAccessFeeCsvRepository(CsvReader reader) {
    this.reader = reader;
  }

  public CampusAccessFee findBy(CarType carType, PeriodType period) {
    Map<Map<CarType, PeriodType>, CampusAccessFee> fees = readAndParseCsv();
    Map<CarType, PeriodType> key = new HashMap<>();
    key.put(carType, period);

    CampusAccessFee fee = fees.get(key);

    if (fee == null) {
      throw new CantFindCampusAccessFeeException(
          "Cant find a campus access fee associated with the given car type and periode");
    }

    return fee;
  }

  private Map<Map<CarType, PeriodType>, CampusAccessFee> readAndParseCsv() {
    Map<Map<CarType, PeriodType>, CampusAccessFee> fees = new HashMap<>();
    List<List<String>> csvData = reader.read(CSV_PATH);
    List<String> csvInfos = new ArrayList<>(csvData.get(0));
    csvData.remove(0);

    Collator collator = Collator.getInstance();
    collator.setStrength(Collator.NO_DECOMPOSITION);

    Map<PeriodType, Integer> periodTypeColumnMap = mapPeriodColumn(csvInfos, collator);

    for (List<String> line : csvData) {
      CarType carType = createCarType(line.get(0), collator);

      for (Entry<PeriodType, Integer> entry : periodTypeColumnMap.entrySet()) {
        Map<CarType, PeriodType> carTypePeriod = new HashMap<>();
        carTypePeriod.put(carType, entry.getKey());
        CampusAccessFee fee = new CampusAccessFee(Double.parseDouble(line.get(entry.getValue())));
        fees.put(carTypePeriod, fee);
      }
    }

    return fees;
  }

  private CarType createCarType(String carTypeString, Collator collator) {
    if (collator.equals("gourmande", carTypeString.toLowerCase())) {
      return CarType.GOURMANDE;
    } else if (collator.equals("economique", carTypeString.toLowerCase())) {
      return CarType.ECONOMIQUE;
    } else if (collator.equals("hybride economique", carTypeString.toLowerCase())) {
      return CarType.HYBRIDE_ECONOMIQUE;
    } else if (collator.equals("super economique", carTypeString.toLowerCase())) {
      return CarType.SUPER_ECONOMIQUE;
    } else if (collator.equals("0 pollution", carTypeString.toLowerCase())) {
      return CarType.SANS_POLLUTION;
    } else {
      return null;
    }
  }

  private Map<PeriodType, Integer> mapPeriodColumn(List<String> csvInfos,
                                                   Collator collator) {
    Map<PeriodType, Integer> periodTypePosition = new HashMap<>();

    for (int i = 1; i < csvInfos.size(); i++) {
      String info = csvInfos.get(i);
      if (collator.equals("1h", info)) {
        periodTypePosition.put(PeriodType.ONE_HOUR, i);
      } else if (collator.equals("1j", info)) {
        periodTypePosition.put(PeriodType.SINGLE_DAY, i);
      } else if (collator.equals("1j/semaine/session", info)) {
        periodTypePosition.put(PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER, i);
      } else if (collator.equals("1 session", info)) {
        periodTypePosition.put(PeriodType.ONE_SEMESTER, i);
      } else if (collator.equals("2 session", info)) {
        periodTypePosition.put(PeriodType.TWO_SEMESTERS, i);
      } else if (collator.equals("3 session", info)) {
        periodTypePosition.put(PeriodType.THREE_SEMESTERS, i);
      }
    }
    return periodTypePosition;
  }
}
