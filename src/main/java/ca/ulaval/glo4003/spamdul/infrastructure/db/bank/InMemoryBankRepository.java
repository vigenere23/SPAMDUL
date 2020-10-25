package ca.ulaval.glo4003.spamdul.infrastructure.db.bank;

import ca.ulaval.glo4003.spamdul.entity.account.Account;
import ca.ulaval.glo4003.spamdul.entity.account.MainBankAccount;
import ca.ulaval.glo4003.spamdul.entity.account.BankRepository;
import java.util.logging.Logger;

public class InMemoryBankRepository implements BankRepository {

  private static final Logger logger = Logger.getLogger(InMemoryBankRepository.class.getName());
  private MainBankAccount mainBankAccount;
  private Account sustainableMobilityProjectAccount;

  public void save(MainBankAccount mainBankAccount) {
    this.mainBankAccount = mainBankAccount;
    String loggingInfos = "Saving Bank accounts";
    logger.info(loggingInfos);
  }

  public MainBankAccount getMainBankAccount() {
    return mainBankAccount;
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
