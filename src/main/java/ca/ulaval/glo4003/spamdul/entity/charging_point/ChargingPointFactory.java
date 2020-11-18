package ca.ulaval.glo4003.spamdul.entity.charging_point;

public class ChargingPointFactory {

  public ChargingPoint create() {
    return new ChargingPoint(new ChargingPointId());
  }
}
