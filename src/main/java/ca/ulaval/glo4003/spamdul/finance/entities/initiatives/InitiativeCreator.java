package ca.ulaval.glo4003.spamdul.finance.entities.initiatives;

import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.exceptions.InsufficientFundsException;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.InitiativeTransactionService;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

public class InitiativeCreator {

  private final InitiativeTransactionService initiativeTransactionService;
  private final InitiativeFactory initiativeFactory;

  public InitiativeCreator(InitiativeTransactionService initiativeTransactionService,
                           InitiativeFactory initiativeFactory) {
    this.initiativeTransactionService = initiativeTransactionService;
    this.initiativeFactory = initiativeFactory;
  }

  public Initiative createInitiative(String name, Amount amount) {
    addExpense(amount);

    return initiativeFactory.create(name, amount);
  }

  public Initiative createInitiative(InitiativeCode code, String name, Amount amount) {
    addExpense(amount);

    return initiativeFactory.create(code, name, amount);
  }

  public Initiative createInitiative(ReservedInitiativeCode reservedCode, String name, Amount amount) {
    addExpense(amount);

    return initiativeFactory.create(reservedCode, name, amount);
  }

  private void addExpense(Amount amount) {
    try {
      initiativeTransactionService.addExpense(amount);
    } catch (ca.ulaval.glo4003.spamdul.finance.entities.exceptions.InsufficientFundsException e) {
      throw new InsufficientFundsException();
    }
  }
}
