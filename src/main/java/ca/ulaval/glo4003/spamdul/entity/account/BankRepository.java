package ca.ulaval.glo4003.spamdul.entity.account;

public interface BankRepository {

  void save(MainBankAccount mainBankAccount);

  MainBankAccount getMainBankAccount();

  void saveSustainableMobilityProjectAccount(Account sustainableMobilityProjectAccount);

  Account getSustainableMobilityProjectAccount();
}
