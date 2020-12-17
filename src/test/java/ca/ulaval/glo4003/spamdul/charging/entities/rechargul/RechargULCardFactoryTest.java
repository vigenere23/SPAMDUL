package ca.ulaval.glo4003.spamdul.charging.entities.rechargul;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RechargULCardFactoryTest {

  private static final RechargULCardId AN_ID = RechargULCardId.valueOf("123");

  private RechargULCardFactory rechargULCardFactory;

  @Mock
  private RechargULCardIdFactory rechargULCardIdFactory;
  @Mock
  private TransactionFactory transactionFactory;

  @Before
  public void setUp() {
    rechargULCardFactory = new RechargULCardFactory(rechargULCardIdFactory, transactionFactory);
  }

  @Test
  public void whenCreating_shouldReturnNewCard() {
    when(rechargULCardIdFactory.create()).thenReturn(AN_ID);
    RechargULCard rechargULCard = rechargULCardFactory.create();
    assertThat(rechargULCard.getId()).isEqualTo(AN_ID);
  }
}
