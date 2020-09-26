package ca.ulaval.glo4003.spamdul.context.usagereport;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogFactory;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogRepository;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingZone;

import java.time.LocalDate;

public class ParkingAccessLogPopulator {
    private ParkingAccessLogRepository parkingAccessLogRepository;
    private ParkingAccessLogFactory parkingAccessLogFactory;

    public ParkingAccessLogPopulator(ParkingAccessLogRepository parkingAccessLogRepository, ParkingAccessLogFactory parkingAccessLogFactory) {
        this.parkingAccessLogRepository = parkingAccessLogRepository;
        this.parkingAccessLogFactory = parkingAccessLogFactory;
    }

    public void populate() {
        ParkingAccessLog log1 = parkingAccessLogFactory.create(ParkingZone.ZONE_1, LocalDate.now());
        ParkingAccessLog log2 = parkingAccessLogFactory.create(ParkingZone.ZONE_1, LocalDate.now());
        ParkingAccessLog log3 = parkingAccessLogFactory.create(ParkingZone.ZONE_2, LocalDate.now());
        ParkingAccessLog log4 = parkingAccessLogFactory.create(ParkingZone.ZONE_3, LocalDate.now());
        ParkingAccessLog log5 = parkingAccessLogFactory.create(ParkingZone.ZONE_R, LocalDate.now());

        parkingAccessLogRepository.save(log1);
        parkingAccessLogRepository.save(log2);
        parkingAccessLogRepository.save(log3);
        parkingAccessLogRepository.save(log4);
        parkingAccessLogRepository.save(log5);
    }
}
