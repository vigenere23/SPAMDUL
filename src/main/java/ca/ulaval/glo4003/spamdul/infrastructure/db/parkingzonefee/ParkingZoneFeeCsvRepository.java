package ca.ulaval.glo4003.spamdul.infrastructure.db.parkingzonefee;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZoneFee;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZoneFeeRepository;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.infrastructure.reader.CsvReader;
import java.text.Collator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ParkingZoneFeeCsvRepository implements ParkingZoneFeeRepository {

  private final String CSV_PATH = "src/main/resources/frais-zone.csv";
  private final CsvReader reader;

  public ParkingZoneFeeCsvRepository(CsvReader csvReader) {
    reader = csvReader;
  }


  public ParkingZoneFee findBy(ParkingZone parkingZone, PeriodType period) {
    Map<ParkingZone, Map<PeriodType, ParkingZoneFee>> fees = readAndParseCsv();
    Map<PeriodType, ParkingZoneFee> periodTypeParkingZoneFeeMap = fees.get(parkingZone);

    if (periodTypeParkingZoneFeeMap == null) {
      throw new CantFindParkingZoneFeeException(
          "Cant find a parking zone fee associated with the given parking zone and period");
    }

    ParkingZoneFee fee = periodTypeParkingZoneFeeMap.get(period);

    if (fee == null) {
      throw new CantFindParkingZoneFeeException(
          "Cant find a parking zone fee associated with the given parking zone and period");
    }

    return fee;
  }

  private Map<ParkingZone, Map<PeriodType, ParkingZoneFee>> readAndParseCsv() {
    Map<ParkingZone, Map<PeriodType, ParkingZoneFee>> fees = new HashMap<>();
    List<List<String>> csvData = reader.read(CSV_PATH);
    List<String> csvInfos = new ArrayList<>(csvData.get(0));
    csvData.remove(0);

    Collator collator = Collator.getInstance();
    collator.setStrength(Collator.NO_DECOMPOSITION);

    Map<PeriodType, Integer> periodTypeColumnMap = mapPeriodColumn(csvInfos, collator);

    for (List<String> line : csvData) {
      ParkingZone parkingZone = ParkingZone.parse(line.get(0), collator);

      for (Entry<PeriodType, Integer> entry : periodTypeColumnMap.entrySet()) {
        ParkingZoneFee fee = new ParkingZoneFee(Double.parseDouble(line.get(entry.getValue())));

        if (fees.get(parkingZone) != null) {
          fees.get(parkingZone).put(entry.getKey(), fee);
        } else {
          Map<PeriodType, ParkingZoneFee> periodFeeMap = new HashMap<>();
          periodFeeMap.put(entry.getKey(), fee);
          fees.put(parkingZone, periodFeeMap);
        }
      }
    }

    return fees;
  }

  public static Map<PeriodType, Integer> mapPeriodColumn(List<String> csvInfos,
                                                         Collator collator) {
    Map<PeriodType, Integer> periodTypePosition = new HashMap<>();

    for (int i = 1; i < csvInfos.size(); i++) {
      String info = csvInfos.get(i);
      PeriodType periodType = PeriodType.parse(info, collator);
      periodTypePosition.put(periodType, i);
    }

    return periodTypePosition;
  }
}
