package ca.ulaval.glo4003.spamdul.usecases.banking;

import ca.ulaval.glo4003.spamdul.entity.account.Bank;
import ca.ulaval.glo4003.spamdul.entity.account.SustainableMobilityProjectAccount;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionDto;
import ca.ulaval.glo4003.spamdul.usecases.transactions.TransactionService;
import ca.ulaval.glo4003.spamdul.utils.Amount;

public class BankingService {

  private SustainableMobilityProjectAccount sustainableMobilityProjectAccount;
  private TransactionService transactionService;
  private Bank bank;

  public BankingService(TransactionService transactionService, Bank bank,
                        SustainableMobilityProjectAccount sustainableMobilityProjectAccount) {
    this.transactionService = transactionService;
    this.bank = bank;
    this.sustainableMobilityProjectAccount = sustainableMobilityProjectAccount;
  }

  public void addFunds(TransactionDto transactionDto) {
    bank.addFunds(Amount.valueOf(transactionDto.amount));
    transactionService.createTransaction(transactionDto);
  }

  public void fundInitiative(Amount amount) {
    sustainableMobilityProjectAccount.withdrawFunds(amount);

  }

}
