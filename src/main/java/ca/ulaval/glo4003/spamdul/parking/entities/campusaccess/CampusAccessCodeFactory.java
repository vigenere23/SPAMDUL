package ca.ulaval.glo4003.spamdul.parking.entities.campusaccess;

import ca.ulaval.glo4003.spamdul.shared.entities.ids.IdGenerator;

public class CampusAccessCodeFactory {

  private final IdGenerator idGenerator;

  public CampusAccessCodeFactory(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  public CampusAccessCode create() {
    return CampusAccessCode.valueOf(idGenerator.generate());
  }
}
