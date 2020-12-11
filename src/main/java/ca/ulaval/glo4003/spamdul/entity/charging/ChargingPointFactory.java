package ca.ulaval.glo4003.spamdul.entity.charging;

public class ChargingPointFactory {

  private final ChargingPointIdFactory chargingPointIdFactory;

  public ChargingPointFactory(ChargingPointIdFactory chargingPointIdFactory) {
    this.chargingPointIdFactory = chargingPointIdFactory;
  }

  public ChargingPoint create() {
    return new ChargingPoint(chargingPointIdFactory.create());
  }
}
