package ca.ulaval.glo4003.spamdul.usecases.carboncredits;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.CarbonCredits;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.CarbonCreditsPurchaser;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.EventSchedulerObservable;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.ScheduleObserver;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.CarbonCreditsBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.SustainabilityBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.exceptions.InsufficientFundsException;
import ca.ulaval.glo4003.spamdul.entity.initiatives.Initiative;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeCode;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeCreator;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions.InvalidInitiativeAmount;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public class CarbonCreditsService implements ScheduleObserver {

  private final EventSchedulerObservable eventSchedulerObservable;
  private final CarbonCreditsPurchaser carbonCreditsPurchaser;
  private final InitiativeRepository initiativeRepository;
  private final InitiativeCreator initiativeCreator;
  private final AccessLevelValidator accessLevelValidator;
  private final CarbonCreditsBankAccount carbonCreditsBankAccount;
  private final SustainabilityBankAccount sustainabilityBankAccount;

  public CarbonCreditsService(EventSchedulerObservable eventSchedulerObservable,
                              CarbonCreditsPurchaser carbonCreditsPurchaser,
                              InitiativeRepository initiativeRepository,
                              InitiativeCreator initiativeCreator,
                              AccessLevelValidator accessLevelValidator,
                              CarbonCreditsBankAccount carbonCreditsBankAccount,
                              SustainabilityBankAccount sustainabilityBankAccount) {
    this.eventSchedulerObservable = eventSchedulerObservable;
    this.carbonCreditsPurchaser = carbonCreditsPurchaser;
    this.initiativeRepository = initiativeRepository;
    this.initiativeCreator = initiativeCreator;
    this.accessLevelValidator = accessLevelValidator;
    this.carbonCreditsBankAccount = carbonCreditsBankAccount;
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

  public double transferRemainingBudget() {
    Amount totalAvailableAmount = sustainabilityBankAccount.getBalance();
    Initiative initiative;

    try {
      initiative = initiativeCreator.createInitiative(new InitiativeCode("MCARB"),
                                                      "Marché du carbone",
                                                      totalAvailableAmount);
    } catch (InvalidInitiativeAmount | InsufficientFundsException exception) {
      return 0;
    }

    carbonCreditsPurchaser.purchase(CarbonCredits.valueOf(totalAvailableAmount));
    carbonCreditsBankAccount.addRevenue(totalAvailableAmount);
    initiativeRepository.save(initiative); // TODO should be done by InitiativeCreator?

    return totalAvailableAmount.asDouble();
  }

  @Override
  public void listenScheduledEvent() {
    transferRemainingBudget();
  }
}
