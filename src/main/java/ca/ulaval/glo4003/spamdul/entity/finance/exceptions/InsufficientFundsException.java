package ca.ulaval.glo4003.spamdul.entity.finance.exceptions;

import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public class InsufficientFundsException extends FinancialException {

  public InsufficientFundsException(String bankAccount, Amount amountToDebit, Amount balance) {
    super(String.format("Not enough funds in %s: wanted to debit %s but balance is %s",
                        bankAccount,
                        amountToDebit.toString(),
                        balance.toString()));
  }
}
