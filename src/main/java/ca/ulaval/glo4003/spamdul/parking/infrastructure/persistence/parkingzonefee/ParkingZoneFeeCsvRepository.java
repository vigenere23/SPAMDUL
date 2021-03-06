package ca.ulaval.glo4003.spamdul.parking.infrastructure.persistence.parkingzonefee;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZoneFeeRepository;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.reader.CsvReader;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.PeriodType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ParkingZoneFeeCsvRepository implements ParkingZoneFeeRepository {

  private final String path;
  private final CsvReader reader;

  public ParkingZoneFeeCsvRepository(CsvReader csvReader, String path) {
    reader = csvReader;
    this.path = path;
  }


  @Override public Amount findBy(ParkingZone parkingZone, PeriodType period) {
    if (parkingZone == ParkingZone.ZONE_BIKE) {
      return Amount.valueOf(0);
    }

    Map<ParkingZone, Map<PeriodType, Amount>> fees = readAndParseCsv();
    Map<PeriodType, Amount> periodTypeParkingZoneFeeMap = fees.get(parkingZone);

    if (periodTypeParkingZoneFeeMap == null) {
      throw new CantFindParkingZoneFeeException();
    }

    Amount fee = periodTypeParkingZoneFeeMap.get(period);

    if (fee == null) {
      throw new CantFindParkingZoneFeeException();
    }

    return fee;
  }

  private Map<ParkingZone, Map<PeriodType, Amount>> readAndParseCsv() {
    Map<ParkingZone, Map<PeriodType, Amount>> fees = new HashMap<>();
    List<List<String>> csvData = reader.read(path);
    List<String> csvInfos = new ArrayList<>(csvData.get(0));
    csvData.remove(0);

    Map<PeriodType, Integer> periodTypeColumnMap = mapPeriodColumn(csvInfos);

    for (List<String> line : csvData) {
      ParkingZone parkingZone = ParkingZone.parse(line.get(0));

      for (Entry<PeriodType, Integer> entry : periodTypeColumnMap.entrySet()) {
        String stringAmount = line.get(entry.getValue());
        Amount fee = Amount.valueOf(stringAmount);

        if (fees.get(parkingZone) != null) {
          fees.get(parkingZone).put(entry.getKey(), fee);
        } else {
          Map<PeriodType, Amount> periodFeeMap = new HashMap<>();
          periodFeeMap.put(entry.getKey(), fee);
          fees.put(parkingZone, periodFeeMap);
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
