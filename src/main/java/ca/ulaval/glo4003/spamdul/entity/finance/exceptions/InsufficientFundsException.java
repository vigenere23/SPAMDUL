package ca.ulaval.glo4003.spamdul.entity.finance.exceptions;

import ca.ulaval.glo4003.spamdul.shared.amount.Amount;

public class InsufficientFundsException extends FinanceException {

  public InsufficientFundsException(String accountName, Amount amountToDebit, Amount balance) {
    super(String.format("Not enough funds in %s bank account: wanted to debit %s but balance is %s",
                        accountName,
                        amountToDebit.toString(),
                        balance.toString()));
  }
}
