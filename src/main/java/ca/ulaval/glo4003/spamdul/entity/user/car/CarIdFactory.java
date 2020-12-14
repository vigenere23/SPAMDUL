package ca.ulaval.glo4003.spamdul.entity.user.car;

import ca.ulaval.glo4003.spamdul.entity.ids.IdGenerator;

public class CarIdFactory {

  private final IdGenerator idGenerator;

  public CarIdFactory(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  public CarId create() {
    return CarId.valueOf(idGenerator.generate());
  }
}
