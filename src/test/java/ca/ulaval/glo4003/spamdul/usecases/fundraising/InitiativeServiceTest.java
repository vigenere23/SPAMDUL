package ca.ulaval.glo4003.spamdul.usecases.fundraising;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.account.Account;
import ca.ulaval.glo4003.spamdul.entity.account.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.account.InsufficientFundsException;
import ca.ulaval.glo4003.spamdul.entity.fundraising.Initiative;
import ca.ulaval.glo4003.spamdul.entity.fundraising.InitiativeFactory;
import ca.ulaval.glo4003.spamdul.entity.fundraising.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.entity.fundraising.exceptions.InvalidInitiativeAmount;
import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionDto;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.usecases.fundraising.dto.InitiativeDto;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import com.google.common.truth.Truth;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InitiativeServiceTest {

  private InitiativeService initiativeService;
  private InitiativeDto A_INITIATIVE_DTO = new InitiativeDto();
  private double A_VALUE = 50;
  private String A_NAME = "initiative vélo";
  private TransactionDto A_TRANSACTION_DTO = new TransactionDto();
  private Amount AN_AMOUNT = Amount.valueOf(100);
  private LocalDateTime NOW = LocalDateTime.now();
  private TransactionType A_TRANSACTION_TYPE = TransactionType.INITIATIVE;
  private Transaction A_TRANSACTION = new Transaction(AN_AMOUNT, NOW, A_TRANSACTION_TYPE);

  @Mock
  private InitiativeRepository initiativeRepository;
  @Mock
  private InitiativeFactory initiativeFactory;
  @Mock
  private BankRepository bankRepository;
  @Mock
  private TransactionFactory transactionFactory;
  @Mock
  private Account account;

  @Before
  public void setUp() {
    initiativeService = new InitiativeService(initiativeRepository,
                                              initiativeFactory,
                                              bankRepository,
                                              transactionFactory);
    A_INITIATIVE_DTO.amount = A_VALUE;
    A_INITIATIVE_DTO.name = A_NAME;
    A_TRANSACTION_DTO.transactionType = TransactionType.INITIATIVE;
    A_TRANSACTION_DTO.amount = A_VALUE * -1;
  }

  @Test
  public void whenGettingAllInitiatives_shouldReturnFromRepository() {
    List<Initiative> mockedInitiatives = new ArrayList<>();
    when(initiativeRepository.findAll()).thenReturn(mockedInitiatives);

    List<Initiative> initiatives = initiativeService.getAllInitiatives();

    Truth.assertThat(initiatives).isEqualTo(mockedInitiatives);
  }

  @Test
  public void givenEnoughFunds_whenAddingAInitiative_shouldCallTransactionFactoryWithInitiativeInformation() {
    when(transactionFactory.create(A_TRANSACTION_DTO)).thenReturn(A_TRANSACTION);
    when(bankRepository.getSustainableMobilityProjectAccount()).thenReturn(account);

    initiativeService.addInitiative(A_INITIATIVE_DTO);

    verify(transactionFactory).create(any());
    verify(account).addTransaction(any());
  }

  @Test(expected = InvalidInitiativeAmount.class)
  public void givenNotEnoughFunds_whenAddingAInitiative_shouldCallTransactionFactoryWithInitiativeInformation() {
    when(transactionFactory.create(A_TRANSACTION_DTO)).thenReturn(A_TRANSACTION);
    when(bankRepository.getSustainableMobilityProjectAccount()).thenThrow(InsufficientFundsException.class);

    initiativeService.addInitiative(A_INITIATIVE_DTO);
  }
}
