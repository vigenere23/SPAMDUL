package ca.ulaval.glo4003.spamdul.finance.usecases.carboncredits;

import ca.ulaval.glo4003.spamdul.authentication.entities.TemporaryToken;
import ca.ulaval.glo4003.spamdul.authentication.entities.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.finance.entities.bank_accounts.SustainabilityBankAccount;
import ca.ulaval.glo4003.spamdul.finance.entities.exceptions.InsufficientFundsException;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.Initiative;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.InitiativeCreator;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.ReservedInitiativeCode;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.carboncredits.CarbonCredits;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.carboncredits.CarbonCreditsPurchaser;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.carboncredits.EventSchedulerObservable;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.carboncredits.ScheduleObserver;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.exceptions.InvalidInitiativeAmountException;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.CarbonCreditsTransactionService;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

public class CarbonCreditsUseCase implements ScheduleObserver {

  private final EventSchedulerObservable eventSchedulerObservable;
  private final CarbonCreditsPurchaser carbonCreditsPurchaser;
  private final InitiativeRepository initiativeRepository;
  private final InitiativeCreator initiativeCreator;
  private final AccessLevelValidator accessLevelValidator;
  private final CarbonCreditsTransactionService carbonCreditsTransactionService;
  private final SustainabilityBankAccount sustainabilityBankAccount;

  public CarbonCreditsUseCase(EventSchedulerObservable eventSchedulerObservable,
                              CarbonCreditsPurchaser carbonCreditsPurchaser,
                              InitiativeRepository initiativeRepository,
                              InitiativeCreator initiativeCreator,
                              AccessLevelValidator accessLevelValidator,
                              CarbonCreditsTransactionService carbonCreditsTransactionService,
                              SustainabilityBankAccount sustainabilityBankAccount) {
    this.eventSchedulerObservable = eventSchedulerObservable;
    this.carbonCreditsPurchaser = carbonCreditsPurchaser;
    this.initiativeRepository = initiativeRepository;
    this.initiativeCreator = initiativeCreator;
    this.accessLevelValidator = accessLevelValidator;
    this.carbonCreditsTransactionService = carbonCreditsTransactionService;
    this.sustainabilityBankAccount = sustainabilityBankAccount;
  }

  public boolean activateAutomaticTransfer(boolean active, TemporaryToken temporaryToken) {
    accessLevelValidator.validate(temporaryToken);

    if (active) {
      eventSchedulerObservable.register(this);
      return true;
    } else {
      eventSchedulerObservable.unregister(this);
      return false;
    }
  }

  public Amount transferRemainingBudget() {
    Amount totalAvailableAmount = sustainabilityBankAccount.getBalance();
    Initiative initiative;

    try {
      initiative = initiativeCreator.createInitiative(ReservedInitiativeCode.CARBON_CREDITS,
                                                      "Marché du carbone",
                                                      totalAvailableAmount);
    } catch (InvalidInitiativeAmountException | InsufficientFundsException exception) {
      return Amount.valueOf(0);
    }

    carbonCreditsPurchaser.purchase(CarbonCredits.valueOf(totalAvailableAmount));
    carbonCreditsTransactionService.addRevenue(totalAvailableAmount);
    initiativeRepository.save(initiative);

    return totalAvailableAmount;
  }

  @Override
  public void listenScheduledEvent() {
    transferRemainingBudget();
  }
}
