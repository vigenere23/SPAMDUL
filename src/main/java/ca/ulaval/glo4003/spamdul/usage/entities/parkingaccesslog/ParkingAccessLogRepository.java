package ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog;

public interface ParkingAccessLogRepository {

  ParkingAccessLogQueryBuilder find();

  void save(ParkingAccessLog parkingAccessLog);
}
