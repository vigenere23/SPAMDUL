package ca.ulaval.glo4003.spamdul.entity.ids;

public class IncrementalLongIdGenerator implements IdGenerator<Long> {

  private Long lastId = 0L;

  @Override
  public synchronized Long getNextId() {
    lastId += 1;

    return lastId;
  }
}
