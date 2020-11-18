package ca.ulaval.glo4003.spamdul.entity.rechargul;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.InvalidRechargULCardCredits;
import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.InvalidRechargULCardDebiting;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RechargULCardTest {

  private final Amount AN_AMOUNT = Amount.valueOf(12.34834);

  private RechargULCard card;

  @Before
  public void setUp() {
    card = new RechargULCard(new RechargULCardId(), new TransactionFactory());
  }

  @Test(expected = InvalidRechargULCardCredits.class)
  public void whenAddingNegativeCredits_shouldThrowException() {
    card.addCredits(Amount.valueOf(-1));
  }

  @Test(expected = InvalidRechargULCardCredits.class)
  public void whenAddingZeroCredits_shouldThrowException() {
    card.addCredits(Amount.valueOf(0));
  }

  @Test(expected = InvalidRechargULCardDebiting.class)
  public void whenDebitingNegativeAmount_shouldThrowException() {
    card.debit(Amount.valueOf(-1));
  }

  @Test(expected = InvalidRechargULCardDebiting.class)
  public void whenDebitingZeroAmount_shouldThrowException() {
    card.debit(Amount.valueOf(0));
  }

  @Test
  public void whenInitialized_shouldHaveZeroCredits() {
    assertThat(card.total()).isEqualTo(Amount.valueOf(0));
  }

  @Test
  public void whenAddingCredits_shouldAddToCredits() {
    card.addCredits(AN_AMOUNT);
    assertThat(card.total()).isEqualTo(AN_AMOUNT);
    assertThat(card.hasEnoughCredits()).isFalse();
  }

  @Test
  public void whenDebiting_shouldSubtractFromCredits() {
    card.debit(AN_AMOUNT);
    assertThat(card.total()).isEqualTo(AN_AMOUNT.multiply(-1));
    assertThat(card.hasEnoughCredits()).isTrue();
  }

  @Test
  public void givenDebitedAmount_whenCreditingSameAmount_shouldHaveZeroBalance() {
    card.addCredits(AN_AMOUNT);
    card.debit(AN_AMOUNT);
    assertThat(card.total()).isEqualTo(Amount.valueOf(0));
    assertThat(card.hasEnoughCredits()).isTrue();
  }
}
