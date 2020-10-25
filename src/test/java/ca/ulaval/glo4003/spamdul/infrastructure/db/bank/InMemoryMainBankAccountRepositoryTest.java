package ca.ulaval.glo4003.spamdul.infrastructure.db.bank;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.account.Account;
import ca.ulaval.glo4003.spamdul.entity.account.MainBankAccount;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InMemoryMainBankAccountRepositoryTest {

  private final Account A_ACCOUNT = new Account();
  private final Account ANOTHER_ACCOUNT = new Account();
  private final double A_RATIO = 0.5;

  @Mock
  private TransactionFactory transactionFactory;

  private MainBankAccount mainBankAccount;
  private InMemoryBankRepository bankRepository;

  @Before
  public void setUp() {
    bankRepository = new InMemoryBankRepository();
    mainBankAccount = new MainBankAccount(transactionFactory, A_ACCOUNT, ANOTHER_ACCOUNT, A_RATIO);
  }

  @Test
  public void whenSavingBank_shouldSaveBankInRepository() {
    bankRepository.save(mainBankAccount);

    assertThat(bankRepository.getMainBankAccount()).isEqualTo(mainBankAccount);
  }

  @Test
  public void whenSavingAccount_shouldSaveAccountInRepository() {
    bankRepository.saveSustainableMobilityProjectAccount(A_ACCOUNT);

    assertThat(bankRepository.getSustainableMobilityProjectAccount()).isEqualTo(A_ACCOUNT);
  }
}
