package ca.ulaval.glo4003.spamdul.account.entities;

import ca.ulaval.glo4003.spamdul.shared.entities.ids.IdGenerator;

public class AccountIdFactory {

  private final IdGenerator idGenerator;

  public AccountIdFactory(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  public AccountId create() {
    return AccountId.valueOf(idGenerator.generate());
  }
}
