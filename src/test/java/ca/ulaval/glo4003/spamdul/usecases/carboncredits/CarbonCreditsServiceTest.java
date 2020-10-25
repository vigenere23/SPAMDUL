package ca.ulaval.glo4003.spamdul.usecases.carboncredits;

import ca.ulaval.glo4003.spamdul.entity.account.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.EventSchedulerObservable;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CarbonCreditsServiceTest {

  @Mock
  private EventSchedulerObservable eventSchedulerObservable;
  @Mock
  private BankRepository bankRepository;
  @Mock
  private TransactionFactory transactionFactory;

  private CarbonCreditsService carbonCreditsService;


  @Before
  public void setUp() {
    carbonCreditsService = new CarbonCreditsService(eventSchedulerObservable, bankRepository, transactionFactory);
  }

  @Test
  public void whenActivateAutomaticTransfer_shouldRegisterToObservable() {
    carbonCreditsService.activateAutomaticTransfer(true);

    verify(eventSchedulerObservable).register(carbonCreditsService);
  }

  @Test
  public void whenDeactivateAutomaticTransfer_shouldUnregisterToObservable() {
    carbonCreditsService.activateAutomaticTransfer(false);

    verify(eventSchedulerObservable).unregister(carbonCreditsService);
  }
}
