package ca.ulaval.glo4003.spamdul.infrastructure.db.bank;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.account.Account;
import ca.ulaval.glo4003.spamdul.entity.account.MainBankAccount;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;

public class InMemoryMainBankAccountRepositoryTest {

  private final MainBankAccount a_Main_BANKAccount = new MainBankAccount(new HashMap<>());
  private final Account A_ACCOUNT = new Account();

  private InMemoryBankRepository bankRepository;

  @Before
  public void setUp() throws Exception {
    bankRepository = new InMemoryBankRepository();
  }

  @Test
  public void whenSavingBank_shouldSaveBankInRepository() {
    bankRepository.save(a_Main_BANKAccount);

    assertThat(bankRepository.getMainBankAccount()).isEqualTo(a_Main_BANKAccount);
  }

  @Test
  public void whenSavingAccount_shouldSaveAccountInRepository() {
    bankRepository.saveSustainableMobilityProjectAccount(A_ACCOUNT);

    assertThat(bankRepository.getSustainableMobilityProjectAccount()).isEqualTo(A_ACCOUNT);
  }
}