package ca.ulaval.glo4003.spamdul.shared.infrastructure.ids;

import ca.ulaval.glo4003.spamdul.shared.entities.ids.IdGenerator;

public class IncrementalIdGenerator implements IdGenerator {

  private Long lastId = 0L;

  @Override
  public synchronized String generate() {
    lastId += 1;

    return String.valueOf(lastId);
  }
}
