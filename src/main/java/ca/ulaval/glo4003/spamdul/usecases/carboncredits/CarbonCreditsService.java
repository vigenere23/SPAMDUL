package ca.ulaval.glo4003.spamdul.usecases.carboncredits;

import ca.ulaval.glo4003.spamdul.entity.carboncredits.ScheduleObserver;
import ca.ulaval.glo4003.spamdul.usecases.fundraising.InitiativeService;
import ca.ulaval.glo4003.spamdul.utils.Amount;

public class CarbonCreditsService implements ScheduleObserver {

  private InitiativeService initiativeService;
  private static boolean active = true;
  private Amount totalCarbonCredits = Amount.valueOf(0);

  public CarbonCreditsService() {
  }

  public boolean setAutomaticTransfer(boolean active) {
    CarbonCreditsService.active = active;

    // TODO reactivate thread if true?

    return CarbonCreditsService.active;
  }

  // TODO:
  public double transferRemainingBudget() {
    if (active) {
      // TODO save bugget in BD
    }
    else {
      // TODO exception or return 0? (thinking about the thread that could crash and not restart)
      // throw new RuntimeException("Could not transfer funds : the feature is not active");
      return 0;
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

  @Override
  public void listenScheduledEvent() {

  }
}
