package ca.ulaval.glo4003.spamdul.infrastructure.db.bank;

import ca.ulaval.glo4003.spamdul.entity.account.Account;
import ca.ulaval.glo4003.spamdul.entity.account.Bank;
import ca.ulaval.glo4003.spamdul.entity.account.BankRepository;
import java.util.logging.Logger;

public class InMemoryBankRepository implements BankRepository {

  private static final Logger logger = Logger.getLogger(InMemoryBankRepository.class.getName());
  private Bank bank;
  private Account sustainableMobilityProjectAccount;

  public void saveBank(Bank bank) {
    this.bank = bank;
    String loggingInfos = "Saving Bank accounts";
    logger.info(loggingInfos);
  }

  public Bank getBank() {
    return bank;
  }

  public Account getSustainableMobilityProjectAccount() {
    return sustainableMobilityProjectAccount;
  }

  public void saveSustainableMobilityProjectAccount(Account sustainableMobilityProjectAccount) {
    this.sustainableMobilityProjectAccount = sustainableMobilityProjectAccount;
    String loggingInfos = "Saving sustainable mobility project account";
    logger.info(loggingInfos);
  }
}
