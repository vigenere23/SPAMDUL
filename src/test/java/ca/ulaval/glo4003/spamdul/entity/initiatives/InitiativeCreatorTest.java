package ca.ulaval.glo4003.spamdul.entity.initiatives;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.InitiativeTransactionService;
import ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions.InsufficientFundsException;
import ca.ulaval.glo4003.spamdul.shared.amount.Amount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InitiativeCreatorTest {

  private static final String A_NAME = "dhja";
  private static final Amount AN_AMOUNT = Amount.valueOf(123);
  private static final InitiativeCode A_CODE = InitiativeCode.valueOf("jhg123");
  private static final ReservedInitiativeCode A_RESERVED_CODE = ReservedInitiativeCode.CARBON_CREDITS;

  private InitiativeCreator initiativeCreator;

  @Mock
  private InitiativeTransactionService initiativeTransactionService;
  @Mock
  private InitiativeFactory initiativeFactory;
  @Mock
  private Initiative AN_INITIATIVE;

  @Before
  public void setUp() {
    initiativeCreator = new InitiativeCreator(initiativeTransactionService, initiativeFactory);
  }

  @Test
  public void whenCreatingInitiative_shouldCreateWithFactory() {
    when(initiativeFactory.create(A_NAME, AN_AMOUNT)).thenReturn(AN_INITIATIVE);
    Initiative initiative = initiativeCreator.createInitiative(A_NAME, AN_AMOUNT);
    assertThat(initiative).isEqualTo(AN_INITIATIVE);
  }

  @Test
  public void givenInitiativeCode_whenCreatingInitiative_shouldCreateWithFactory() {
    when(initiativeFactory.create(A_CODE, A_NAME, AN_AMOUNT)).thenReturn(AN_INITIATIVE);
    Initiative initiative = initiativeCreator.createInitiative(A_CODE, A_NAME, AN_AMOUNT);
    assertThat(initiative).isEqualTo(AN_INITIATIVE);
  }

  @Test
  public void givenReservedInitiativeCode_whenCreatingInitiative_shouldCreateWithFactory() {
    when(initiativeFactory.create(A_RESERVED_CODE, A_NAME, AN_AMOUNT)).thenReturn(AN_INITIATIVE);
    Initiative initiative = initiativeCreator.createInitiative(A_RESERVED_CODE, A_NAME, AN_AMOUNT);
    assertThat(initiative).isEqualTo(AN_INITIATIVE);
  }

  @Test
  public void whenCreatingInitiative_shouldAddExpenseToTransactionService() {
    initiativeCreator.createInitiative(A_NAME, AN_AMOUNT);
    verify(initiativeTransactionService).addExpense(AN_AMOUNT);
  }

  @Test
  public void whenCreatingInitiativeWithInitiativeCode_shouldAddExpenseToTransactionService() {
    initiativeCreator.createInitiative(A_CODE, A_NAME, AN_AMOUNT);
    verify(initiativeTransactionService).addExpense(AN_AMOUNT);
  }

  @Test
  public void whenCreatingInitiativeWithReservedInitiativeCode_shouldAddExpenseToTransactionService() {
    initiativeCreator.createInitiative(A_RESERVED_CODE, A_NAME, AN_AMOUNT);
    verify(initiativeTransactionService).addExpense(AN_AMOUNT);
  }

  @Test(expected = InsufficientFundsException.class)
  public void givenInsufficientFunds_whenCreatingInitiative_shouldThrowException() {
    doThrow(ca.ulaval.glo4003.spamdul.entity.finance.exceptions.InsufficientFundsException.class)
        .when(initiativeTransactionService).addExpense(AN_AMOUNT);
    initiativeCreator.createInitiative(A_NAME, AN_AMOUNT);
  }
}
