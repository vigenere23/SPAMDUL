package ca.ulaval.glo4003.spamdul.infrastructure.db.bank;

import ca.ulaval.glo4003.spamdul.entity.bank.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.bank.CarbonCreditsBankAccount;
import ca.ulaval.glo4003.spamdul.entity.bank.MainBankAccount;
import ca.ulaval.glo4003.spamdul.entity.bank.SustainabilityBankAccount;
import java.util.logging.Logger;

public class InMemoryBankRepository implements BankRepository {

  private static final Logger logger = Logger.getLogger(InMemoryBankRepository.class.getName());
  private MainBankAccount mainBankAccount;
  private SustainabilityBankAccount sustainabilityBankAccount;
  private CarbonCreditsBankAccount carbonCreditsBankAccount;

  @Override public MainBankAccount getMainBankAccount() {
    return mainBankAccount;
  }

  @Override public SustainabilityBankAccount getSustainabilityBankAccount() {
    return sustainabilityBankAccount;
  }

  @Override public CarbonCreditsBankAccount getCarbonCreditsBankAccount() {
    return carbonCreditsBankAccount;
  }

  @Override public void save(MainBankAccount mainBankAccount) {
    this.mainBankAccount = mainBankAccount;
    String loggingInfos = "Saving main bank accounts";
    logger.info(loggingInfos);
  }

  @Override
  public void save(SustainabilityBankAccount sustainabilityBankAccount) {
    this.sustainabilityBankAccount = sustainabilityBankAccount;
    String loggingInfos = "Saving sustainable mobility project bank account";
    logger.info(loggingInfos);
  }

  @Override
  public void save(CarbonCreditsBankAccount carbonCreditsBankAccount) {
    this.carbonCreditsBankAccount = carbonCreditsBankAccount;
    String loggingInfos = "Saving carbon credits bank account";
    logger.info(loggingInfos);
  }
}
