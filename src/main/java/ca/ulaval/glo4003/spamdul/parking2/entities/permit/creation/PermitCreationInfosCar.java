package ca.ulaval.glo4003.spamdul.parking2.entities.permit.creation;

public class PermitCreationInfosCar {

  private final CarCreationInfos carCreationInfos;

  public PermitCreationInfosCar(CarCreationInfos carCreationInfos) {
    this.carCreationInfos = carCreationInfos;
  }

  public CarCreationInfos getCar() {
    return carCreationInfos;
  }
}
