package ca.ulaval.glo4003.spamdul.entity.initiatives;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions.InvalidInitiativeAmountException;
import ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions.InvalidInitiativeNameException;
import ca.ulaval.glo4003.spamdul.shared.amount.Amount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InitiativeFactoryTest {

  private final String A_VALID_NAME = "YOLO";
  private final Amount A_VALID_AMOUNT = Amount.valueOf(2233.23);

  @Mock
  private InitiativeIdFactory initiativeIdFactory;
  @Mock
  private InitiativeCodeFactory initiativeCodeFactory;
  @Mock
  private InitiativeCode A_CODE;

  private InitiativeFactory initiativeFactory;

  @Before
  public void setUp() {
    initiativeFactory = new InitiativeFactory(initiativeIdFactory, initiativeCodeFactory);
  }

  @Test(expected = InvalidInitiativeNameException.class)
  public void givenNullName_whenCreating_shouldThrowInvalidInitiativeNameException() {
    initiativeFactory.create(null, A_VALID_AMOUNT);
  }

  @Test(expected = InvalidInitiativeNameException.class)
  public void givenEmptyName_whenCreating_shouldThrowInvalidInitiativeNameException() {
    initiativeFactory.create("", A_VALID_AMOUNT);
  }

  @Test(expected = InvalidInitiativeAmountException.class)
  public void givenNegativeAmount_whenCreating_shouldThrowInvalidInitiativeAmountException() {
    initiativeFactory.create(A_VALID_NAME, Amount.valueOf(-45.21));
  }

  @Test(expected = InvalidInitiativeAmountException.class)
  public void givenZeroAmount_whenCreating_shouldThrowInvalidInitiativeAmountException() {
    initiativeFactory.create(A_VALID_NAME, Amount.valueOf(0));
  }

  @Test
  public void givenReservedCode_whenCreatingWithReservedCode_shouldReturnInitiative() {
    initiativeFactory.create(ReservedInitiativeCode.CARBON_CREDITS, A_VALID_NAME, A_VALID_AMOUNT);
  }

  @Test
  public void whenCreating_itReturnsValidInitiative() {
    when(initiativeCodeFactory.create()).thenReturn(A_CODE);
    assertThat(initiativeFactory.create(A_VALID_NAME, A_VALID_AMOUNT)).isNotNull();
  }
}
