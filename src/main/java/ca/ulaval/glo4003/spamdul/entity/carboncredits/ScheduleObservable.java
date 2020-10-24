package ca.ulaval.glo4003.spamdul.entity.carboncredits;

import java.util.ArrayList;
import java.util.List;

public class TransferFundsToCarbonCreditsObservable {

  private List<TransferFundsToCarbonCreditsObserver> observers = new ArrayList<>();

  public void register(TransferFundsToCarbonCreditsObserver observer) {
      observers.add(observer);
  }

  public void unregister(TransferFundsToCarbonCreditsObserver observer) {
    observers.remove(observer);
  }

  public void notifyObservers() {
    for (TransferFundsToCarbonCreditsObserver observer : observers) {
      observer.transferRemainingFundsToCarbonCredits();
    }
  }

}
