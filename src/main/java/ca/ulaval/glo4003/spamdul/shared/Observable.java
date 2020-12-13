package ca.ulaval.glo4003.spamdul.shared;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable<T> {

  protected final List<T> observers = new ArrayList<>();

  public void register(T observer) {
    observers.add(observer);
  }

  public void unregister(T observer) {
    observers.remove(observer);
  }
}
