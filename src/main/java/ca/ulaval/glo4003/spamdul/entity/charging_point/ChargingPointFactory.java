package ca.ulaval.glo4003.spamdul.entity.charging_point;

public class ChargingPointFactory {

  public ChargingPoint create(ChargingRate chargingRate) {
    return new ChargingPoint(new ChargingPointId(), chargingRate);
  }
}
