package ca.ulaval.glo4003.spamdul.parking2.infrastructure.persistence;

import ca.ulaval.glo4003.spamdul.parking2.entities.ParkingZoneFeeRepository;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodType;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.reader.CsvReader;
import java.util.Map;

public class ParkingZoneFeePopulatorCsv implements ParkingZoneFeePopulator {

  private final CsvReader csvReader;
  private final String path;

  public ParkingZoneFeePopulatorCsv(CsvReader csvReader, String path) {
    this.csvReader = csvReader;
    this.path = path;
  }

  @Override public void populate(ParkingZoneFeeRepository parkingZoneFeeRepository) {
    Map<String, Map<String, String>> data = csvReader.readTable(path);

    data.keySet().forEach(parkingZoneString -> {
      ParkingZone parkingZone = ParkingZone.parse(parkingZoneString);
      Map<String, String> row = data.get(parkingZoneString);
      row.keySet().forEach(accessPeriodTypeString -> {
        AccessPeriodType accessPeriodType = AccessPeriodType.parse(accessPeriodTypeString);
        Amount fee = Amount.valueOf(row.get(accessPeriodTypeString));
        parkingZoneFeeRepository.save(parkingZone, accessPeriodType, fee);
      });
    });
  }
}
