package ca.ulaval.glo4003.spamdul.entity.finance.exceptions;

import ca.ulaval.glo4003.spamdul.shared.amount.Amount;

public class InsufficientFundsException extends FinanceException {

  private String accountName;
  private Amount amountToDebit;
  private Amount balance;

  public InsufficientFundsException(String accountName, Amount amountToDebit, Amount balance) {
    this.accountName = accountName;
    this.amountToDebit = amountToDebit;
    this.balance = balance;
  }

  public String getError() {
    return "INSUFFICIENT_FUNDS_EXCEPTION";
  }

  public String getDescription() {
    return String.format("Not enough funds in %s bank account: wanted to debit %s but balance is %s",
                         accountName,
                         amountToDebit.toString(),
                         balance.toString());
  }
}
