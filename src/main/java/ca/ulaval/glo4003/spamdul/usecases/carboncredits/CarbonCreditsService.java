package ca.ulaval.glo4003.spamdul.usecases.carboncredits;

public class CarbonCreditsService {

  private static boolean active = true;

  public CarbonCreditsService() {
  }

  public boolean toggle(boolean _active) {
    active = _active;

    // TODO reactivate thread if true?

    return active;
  }

  public double transferRemainingBudget() {
    if (!active) {
      // TODO exception or return 0? (thinking about the thread that could crash and not restart)
      throw new RuntimeException("Could not transfer funds : the feature is not active");
    }

    // TODO add logic for transferring
    // TODO need to restart thread?

    return 238.34;
  }

  public double getTotalCarbonCredits() {
    // TODO add logic to get total of transferred carbon credits
    // TODO maybe from TransactionRepository or CarbonCreditBankAccount
    return 1233.34;
  }
}
