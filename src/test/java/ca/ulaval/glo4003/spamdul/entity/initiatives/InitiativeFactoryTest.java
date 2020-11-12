package ca.ulaval.glo4003.spamdul.entity.initiatives;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions.InvalidInitiativeAmount;
import ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions.InvalidInitiativeName;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import org.junit.Before;
import org.junit.Test;

public class InitiativeFactoryTest {

  private final String A_VALID_NAME = "YOLO";
  private final Amount A_VALID_AMOUNT = Amount.valueOf(2233.23);

  private InitiativeFactory initiativeFactory;

  @Before
  public void setUp() {
    initiativeFactory = new InitiativeFactory();
  }

  @Test(expected = InvalidInitiativeName.class)
  public void givenNullName_whenCreating_shouldThrowInvalidInitiativeNameException() {
    initiativeFactory.create(null, A_VALID_AMOUNT);
  }

  @Test(expected = InvalidInitiativeName.class)
  public void givenEmptyName_whenCreating_shouldThrowInvalidInitiativeNameException() {
    initiativeFactory.create("", A_VALID_AMOUNT);
  }

  @Test(expected = InvalidInitiativeAmount.class)
  public void givenNegativeAmount_whenCreating_shouldThrowInvalidInitiativeAmountException() {
    initiativeFactory.create(A_VALID_NAME, Amount.valueOf(-45.21));
  }

  @Test(expected = InvalidInitiativeAmount.class)
  public void givenZeroAmount_whenCreating_shouldThrowInvalidInitiativeAmountException() {
    initiativeFactory.create(A_VALID_NAME, Amount.valueOf(0));
  }

  @Test
  public void whenCreating_itReturnsValidInitiative() {
    assertThat(initiativeFactory.create(A_VALID_NAME, A_VALID_AMOUNT)).isNotNull();
  }
}
