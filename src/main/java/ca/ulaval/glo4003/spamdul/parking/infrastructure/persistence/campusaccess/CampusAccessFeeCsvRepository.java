package ca.ulaval.glo4003.spamdul.parking.infrastructure.persistence.campusaccess;

import ca.ulaval.glo4003.spamdul.parking.entities.campusaccess.CampusAccessFeeRepository;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.CarType;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.reader.CsvReader;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.PeriodType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CampusAccessFeeCsvRepository implements CampusAccessFeeRepository {

  private final String path;
  private final CsvReader reader;

  public CampusAccessFeeCsvRepository(CsvReader reader, String path) {
    this.reader = reader;
    this.path = path;
  }

  @Override public Amount findBy(CarType carType, PeriodType period) {
    Map<CarType, Map<PeriodType, Amount>> fees = readAndParseCsv();
    Map<PeriodType, Amount> periodTypeCampusAccessFeeMap = fees.get(carType);

    if (periodTypeCampusAccessFeeMap == null) {
      throw new CantFindCarTypeAccessFeeException();
    }

    Amount fee = periodTypeCampusAccessFeeMap.get(period);

    if (fee == null) {
      throw new CantFindCarTypeAccessFeeException();
    }

    return fee;
  }

  private Map<CarType, Map<PeriodType, Amount>> readAndParseCsv() {
    Map<CarType, Map<PeriodType, Amount>> fees = new HashMap<>();
    List<List<String>> csvData = reader.read(path);
    List<String> csvInfos = new ArrayList<>(csvData.get(0));
    csvData.remove(0);

    Map<PeriodType, Integer> periodTypeColumnMap = mapPeriodColumn(csvInfos);

    for (List<String> line : csvData) {
      CarType carType = CarType.parse(line.get(0));

      for (Entry<PeriodType, Integer> entry : periodTypeColumnMap.entrySet()) {
        Amount fee = Amount.valueOf(line.get(entry.getValue()));

        if (fees.get(carType) != null) {
          fees.get(carType).put(entry.getKey(), fee);
        } else {
          Map<PeriodType, Amount> periodFeeMap = new HashMap<>();
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
