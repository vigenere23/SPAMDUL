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

  private final String CSV_PATH = "src/main/resources/frais-acces.csv";
  private final CsvReader reader;

  public CampusAccessFeeCsvRepository(CsvReader reader) {
    this.reader = reader;
  }

  public CampusAccessFee findBy(CarType carType, PeriodType period) {
    Map<CarType, Map<PeriodType, CampusAccessFee>> fees = readAndParseCsv();
    Map<PeriodType, CampusAccessFee> periodTypeCampusAccessFeeMap = fees.get(carType);

    if (periodTypeCampusAccessFeeMap == null) {
      throw new CantFindCampusAccessFeeException(
          "Cant find a campus access fee associated with the given car type and periode");
    }

    CampusAccessFee fee = periodTypeCampusAccessFeeMap.get(period);

    if (fee == null) {
      throw new CantFindCampusAccessFeeException(
          "Cant find a campus access fee associated with the given car type and periode");
    }

    return fee;
  }

  private Map<CarType, Map<PeriodType, CampusAccessFee>> readAndParseCsv() {
    Map<CarType, Map<PeriodType, CampusAccessFee>> fees = new HashMap<>();
    List<List<String>> csvData = reader.read(CSV_PATH);
    List<String> csvInfos = new ArrayList<>(csvData.get(0));
    csvData.remove(0);

    Map<PeriodType, Integer> periodTypeColumnMap = mapPeriodColumn(csvInfos);

    for (List<String> line : csvData) {
      CarType carType = CarType.parse(line.get(0));

      for (Entry<PeriodType, Integer> entry : periodTypeColumnMap.entrySet()) {
        CampusAccessFee fee = new CampusAccessFee(Double.parseDouble(line.get(entry.getValue())));

        if (fees.get(carType) != null) {
          fees.get(carType).put(entry.getKey(), fee);
        } else {
          Map<PeriodType, CampusAccessFee> periodFeeMap = new HashMap<>();
          periodFeeMap.put(entry.getKey(), fee);
          fees.put(carType, periodFeeMap);
        }
      }
    }

    return fees;
  }

  public static Map<PeriodType, Integer> mapPeriodColumn(List<String> csvInfos) {
    Map<PeriodType, Integer> periodTypePosition = new HashMap<>();

    for (int i = 1; i < csvInfos.size(); i++) {
      String info = csvInfos.get(i);
      PeriodType periodType = PeriodType.parse(info);
      periodTypePosition.put(periodType, i);
    }

    return periodTypePosition;
  }
}
