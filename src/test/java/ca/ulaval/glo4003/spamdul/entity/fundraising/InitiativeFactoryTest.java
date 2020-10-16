package ca.ulaval.glo4003.spamdul.entity.fundraising;

import ca.ulaval.glo4003.spamdul.entity.fundraising.exceptions.InvalidInitiativeAmount;
import ca.ulaval.glo4003.spamdul.entity.fundraising.exceptions.InvalidInitiativeName;
import org.junit.Before;
import org.junit.Test;

public class InitiativeFactoryTest {

  private final String A_VALID_NAME = "YOLO";
  private final double A_VALID_AMOUNT = 2233.23;

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
    initiativeFactory.create(A_VALID_NAME, -45.21);
  }

  @Test(expected = InvalidInitiativeAmount.class)
  public void givenZeroAmount_whenCreating_shouldThrowInvalidInitiativeAmountException() {
    initiativeFactory.create(A_VALID_NAME, 0);
  }

  @Test
  public void whenCreating_itReturnsValidInitiative() {
    initiativeFactory.create(A_VALID_NAME, A_VALID_AMOUNT);
  }
}
