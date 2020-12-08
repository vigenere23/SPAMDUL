package ca.ulaval.glo4003.spamdul.context.usagereport;

import ca.ulaval.glo4003.spamdul.context.Populator;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogFactory;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogRepository;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ParkingAccessLogPopulator implements Populator {

  private final ParkingAccessLogRepository parkingAccessLogRepository;
  private final ParkingAccessLogFactory parkingAccessLogFactory;

  public ParkingAccessLogPopulator(ParkingAccessLogRepository parkingAccessLogRepository,
                                   ParkingAccessLogFactory parkingAccessLogFactory) {
    this.parkingAccessLogRepository = parkingAccessLogRepository;
    this.parkingAccessLogFactory = parkingAccessLogFactory;
  }

  @Override public void populate(int numberOfRecords) {
    Random random = new Random();
    LocalDate now = LocalDate.now();

    List<ParkingZone> allZones = Arrays.asList(ParkingZone.values());

    for (int recordNumber = 0; recordNumber < numberOfRecords; recordNumber++) {
      ParkingAccessLog accessLog = createRandomLog(random, now, allZones);
      parkingAccessLogRepository.save(accessLog);
    }
  }

  private ParkingAccessLog createRandomLog(Random random, LocalDate now, List<ParkingZone> allZones) {
    LocalDate accessDay = now.minusDays(random.nextInt(40)); // a bit more than a month
    ParkingZone zone = allZones.get(random.nextInt(allZones.size()));

    return parkingAccessLogFactory.create(zone, accessDay);
  }
}
