package ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.RevenueResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.revenue.TransactionAssembler;
import ca.ulaval.glo4003.spamdul.usecases.transactions.TransactionService;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import com.google.common.truth.Truth;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RevenueResourceImplTest {

  public static final int AN_AMOUNT_NUMBER = 99;
  public static final Amount AN_AMOUNT = new Amount(AN_AMOUNT_NUMBER);
  @Mock
  private TransactionService transactionService;
  private TransactionAssembler transactionAssembler;
  private RevenueResourceImpl resource;

  @Before
  public void setUp() throws Exception {
    transactionAssembler = new TransactionAssembler();
    resource = new RevenueResourceImpl(transactionService, transactionAssembler);
  }

  @Test
  public void whenGettingCampusAccessTotalRevenue_shouldReturnResponseWithCorrectInfo() {
    BDDMockito.given(transactionService.getCampusAccessTotalRevenue()).willReturn(AN_AMOUNT);

    RevenueResponse campusAccessTotalRevenue = resource.getCampusAccessTotalRevenue();

    Truth.assertThat(campusAccessTotalRevenue.revenue).isEqualTo(AN_AMOUNT_NUMBER);
  }
}