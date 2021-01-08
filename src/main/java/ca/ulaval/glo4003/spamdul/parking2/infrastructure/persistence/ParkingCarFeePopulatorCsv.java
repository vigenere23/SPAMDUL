package ca.ulaval.glo4003.spamdul.parking2.infrastructure.persistence;

import ca.ulaval.glo4003.spamdul.parking2.entities.ParkingCarFeeRepository;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodType;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.CarType;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.reader.CsvReader;
import java.util.Map;

public class ParkingCarFeePopulatorCsv implements ParkingCarFeePopulator {

  private final CsvReader csvReader;
  private final String path;

  public ParkingCarFeePopulatorCsv(CsvReader csvReader, String path) {
    this.csvReader = csvReader;
    this.path = path;
  }

  @Override public void populate(ParkingCarFeeRepository parkingCarFeeRepository) {
    Map<String, Map<String, String>> data = csvReader.readTable(path);

    data.keySet().forEach(parkingZoneString -> {
      CarType carType = CarType.parse(parkingZoneString);
      Map<String, String> row = data.get(parkingZoneString);
      row.keySet().forEach(accessPeriodTypeString -> {
        AccessPeriodType accessPeriodType = AccessPeriodType.parse(accessPeriodTypeString);
        Amount fee = Amount.valueOf(row.get(accessPeriodTypeString));
        parkingCarFeeRepository.save(carType, accessPeriodType, fee);
      });
    });
  }
}
