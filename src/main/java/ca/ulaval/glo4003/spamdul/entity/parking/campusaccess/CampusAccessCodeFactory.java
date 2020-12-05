package ca.ulaval.glo4003.spamdul.entity.parking.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.ids.IdGenerator;

public class CampusAccessCodeFactory {

  private final IdGenerator<Long> idGenerator;

  public CampusAccessCodeFactory(IdGenerator<Long> idGenerator) {
    this.idGenerator = idGenerator;
  }

  public CampusAccessCode create() {
    return CampusAccessCode.valueOf(String.valueOf(idGenerator.generateId()));
  }
}
