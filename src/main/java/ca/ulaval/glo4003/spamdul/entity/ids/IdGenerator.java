package ca.ulaval.glo4003.spamdul.entity.ids;

public interface IdGenerator<T> {

  T getNextId();
}
