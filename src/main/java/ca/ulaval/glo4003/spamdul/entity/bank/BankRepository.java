package ca.ulaval.glo4003.spamdul.entity.bank;

public interface BankRepository {

  MainBankAccount getMainBankAccount();

  SustainabilityBankAccount getSustainabilityBankAccount();

  CarbonCreditsBankAccount getCarbonCreditsBankAccount();

  void save(MainBankAccount mainBankAccount);

  void save(SustainabilityBankAccount sustainabilityBankAccount);

  void save(CarbonCreditsBankAccount carbonCreditsBankAccount);
}
