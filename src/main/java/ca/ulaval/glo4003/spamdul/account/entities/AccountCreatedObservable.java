package ca.ulaval.glo4003.spamdul.account.entities;

import java.util.ArrayList;
import java.util.List;

public class AccountCreatedObservable {

  private final List<AccountCreatedObserver> observers = new ArrayList<>();

  public void register(AccountCreatedObserver observer) {
    observers.add(observer);
  }

  public void unregister(AccountCreatedObserver observer) {
    observers.remove(observer);
  }

  public void notify(AccountId accountId) {
    observers.forEach(observer -> observer.handleAccountCreated(accountId));
  }
}
