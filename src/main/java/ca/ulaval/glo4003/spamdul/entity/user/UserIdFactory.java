package ca.ulaval.glo4003.spamdul.entity.user;

import ca.ulaval.glo4003.spamdul.entity.ids.IdGenerator;

public class UserIdFactory {

  private final IdGenerator<Long> idGenerator;

  public UserIdFactory(IdGenerator<Long> idGenerator) {
    this.idGenerator = idGenerator;
  }

  public UserId create() {
    return UserId.valueOf(String.valueOf(idGenerator.generateId()));
  }
}
