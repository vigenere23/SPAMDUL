package ca.ulaval.glo4003.spamdul.infrastructure.ui.parkingaccesslog.dto;

public class ParkingAccessLog {
  public int access;

  @Override
  public String toString() {
    return "ParkingAccessLogDto{" +
        ", access='" + access + '\'' +
        '}';
  }
}
