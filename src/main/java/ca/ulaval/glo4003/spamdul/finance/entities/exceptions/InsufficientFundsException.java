package ca.ulaval.glo4003.spamdul.finance.entities.exceptions;

import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

public class InsufficientFundsException extends FinanceException {

  private final String accountName;
  private final Amount amountToDebit;
  private final Amount balance;

  public InsufficientFundsException(String accountName, Amount amountToDebit, Amount balance) {
    this.accountName = accountName;
    this.amountToDebit = amountToDebit;
    this.balance = balance;
  }

  @Override public String getError() {
    return "INSUFFICIENT_FUNDS_EXCEPTION";
  }

  @Override public String getDescription() {
    return String.format("Not enough funds in %s bank account: wanted to debit %s but balance is %s",
                         accountName,
                         amountToDebit.toString(),
                         balance.toString());
  }
}
