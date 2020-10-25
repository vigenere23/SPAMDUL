package ca.ulaval.glo4003.spamdul.entity.idgenerator;

public class IncrementalLongIdGenerator implements IdGenerator<Long> {

  private Long lastId = 0L;

  @Override
  public synchronized Long getNextId() {
    lastId += 1;

    return lastId;
  }
}
