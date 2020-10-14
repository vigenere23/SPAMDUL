package ca.ulaval.glo4003.spamdul.entity.fundraising;

import ca.ulaval.glo4003.spamdul.entity.fundraising.exceptions.InvalidInitiativeAmount;
import ca.ulaval.glo4003.spamdul.entity.fundraising.exceptions.InvalidInitiativeName;
import org.junit.Before;
import org.junit.Test;

public class InitiativeFactoryTest {

  private final String A_NAME = "YOLO";
  private final double AN_AMOUNT = 2233.23;

  private InitiativeFactory initiativeFactory;

  @Before
  public void setUp() {
    initiativeFactory = new InitiativeFactory();
  }

  @Test(expected = InvalidInitiativeName.class)
  public void givenNullName_whenCreating_shouldThrowInvalidInitiativeNameException() {
    initiativeFactory.create(null, AN_AMOUNT);
  }

  @Test(expected = InvalidInitiativeName.class)
  public void givenEmptyName_whenCreating_shouldThrowInvalidInitiativeNameException() {
    initiativeFactory.create("", AN_AMOUNT);
  }

  @Test(expected = InvalidInitiativeAmount.class)
  public void givenNegativeAmount_whenCreating_shouldThrowInvalidInitiativeAmountException() {
    initiativeFactory.create(A_NAME, -45.21);
  }

  @Test(expected = InvalidInitiativeAmount.class)
  public void givenZeroAmount_whenCreating_shouldThrowInvalidInitiativeAmountException() {
    initiativeFactory.create(A_NAME, 0);
  }
}
