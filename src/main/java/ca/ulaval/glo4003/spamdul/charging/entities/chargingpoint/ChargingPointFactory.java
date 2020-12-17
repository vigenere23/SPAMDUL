package ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint;

public class ChargingPointFactory {

  private final ChargingPointIdFactory chargingPointIdFactory;

  public ChargingPointFactory(ChargingPointIdFactory chargingPointIdFactory) {
    this.chargingPointIdFactory = chargingPointIdFactory;
  }

  public ChargingPoint create() {
    return new ChargingPoint(chargingPointIdFactory.create());
  }
}
