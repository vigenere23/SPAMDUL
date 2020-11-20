package ca.ulaval.glo4003.spamdul.infrastructure.db.bank;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.bank.MainBankAccount;
import ca.ulaval.glo4003.spamdul.entity.bank.SustainabilityBankAccount;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InMemoryBankRepositoryTest {

  private final SustainabilityBankAccount a_SustainabilityBank_ACCOUNT = new SustainabilityBankAccount();
  private final SustainabilityBankAccount ANOTHER_SustainabilityBank_ACCOUNT = new SustainabilityBankAccount();
  private final double A_RATIO = 0.5;

  @Mock
  private TransactionFactory transactionFactory;

  private MainBankAccount mainBankAccount;
  private InMemoryBankRepository bankRepository;

  @Before
  public void setUp() {
    bankRepository = new InMemoryBankRepository();
    mainBankAccount = new MainBankAccount(transactionFactory,
                                          a_SustainabilityBank_ACCOUNT,
                                          ANOTHER_SustainabilityBank_ACCOUNT, A_RATIO);
  }

  @Test
  public void whenSavingBank_shouldSaveBankInRepository() {
    bankRepository.save(mainBankAccount);

    assertThat(bankRepository.getMainBankAccount()).isEqualTo(mainBankAccount);
  }

  @Test
  public void whenSavingAccount_shouldSaveAccountInRepository() {
    bankRepository.save(a_SustainabilityBank_ACCOUNT);

    assertThat(bankRepository.getSustainabilityBankAccount()).isEqualTo(a_SustainabilityBank_ACCOUNT);
  }
}
