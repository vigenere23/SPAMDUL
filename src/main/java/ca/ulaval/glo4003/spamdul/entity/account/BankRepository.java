package ca.ulaval.glo4003.spamdul.entity.account;

public interface BankRepository {

  void saveBank(Bank bank);

  Bank getBank();

  void saveSustainableMobilityProjectAccount(Account sustainableMobilityProjectAccount);

  Account getSustainableMobilityProjectAccount();
}
