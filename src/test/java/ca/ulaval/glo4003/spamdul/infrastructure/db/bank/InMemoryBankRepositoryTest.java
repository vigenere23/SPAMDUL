package ca.ulaval.glo4003.spamdul.infrastructure.db.bank;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.*;

import ca.ulaval.glo4003.spamdul.entity.account.Account;
import ca.ulaval.glo4003.spamdul.entity.account.AccountTest;
import ca.ulaval.glo4003.spamdul.entity.account.Bank;
import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.infrastructure.db.car.InMemoryCarRepository;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;

public class InMemoryBankRepositoryTest {

  private final Bank A_BANK = new Bank(new HashMap<>());
  private final Account A_ACCOUNT = new Account();

  private InMemoryBankRepository bankRepository;

  @Before
  public void setUp() throws Exception {
    bankRepository = new InMemoryBankRepository();
  }

  @Test
  public void whenSavingBank_shouldSaveBankInRepository() {
    bankRepository.saveBank(A_BANK);

    assertThat(bankRepository.getBank()).isEqualTo(A_BANK);
  }

  @Test
  public void whenSavingAccount_shouldSaveAccountInRepository() {
    bankRepository.saveSustainableMobilityProjectAccount(A_ACCOUNT);

    assertThat(bankRepository.getSustainableMobilityProjectAccount()).isEqualTo(A_ACCOUNT);
  }
}