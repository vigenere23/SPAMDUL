package ca.ulaval.glo4003.spamdul.parking2.entities.permit.creation;

public class PermitCreationInfos {

  private final CarCreationInfos carCreationInfos;

  public PermitCreationInfos(CarCreationInfos carCreationInfos) {
    this.carCreationInfos = carCreationInfos;
  }

  public PermitCreationInfosCar forCar() {
    return new PermitCreationInfosCar(carCreationInfos);
  }
}
