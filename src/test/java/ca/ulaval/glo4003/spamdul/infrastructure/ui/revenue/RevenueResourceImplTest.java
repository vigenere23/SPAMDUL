package ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.CarTypeTotalRevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.RevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.TotalRevenueResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.revenue.RevenueAssembler;
import ca.ulaval.glo4003.spamdul.usecases.transactions.TransactionService;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import com.google.common.truth.Truth;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RevenueResourceImplTest {

  public static final int AN_AMOUNT_NUMBER = 9;
  public static final Amount AN_AMOUNT = new Amount(AN_AMOUNT_NUMBER);
  public static final int ANOTHER_AMOUNT_NUMBER = 8;
  public static final Amount ANOTHER_AMOUNT = new Amount(ANOTHER_AMOUNT_NUMBER);
  public static final int ANOTHER_ANOTHER_AMOUNT_NUMBER = 11;
  public static final Amount ANOTHER_ANOTHER_AMOUNT = new Amount(ANOTHER_ANOTHER_AMOUNT_NUMBER);
  public static final int GOUMANDE_AMOUNT_NUMBER = 99;
  public static final int ECONOMIQUE_AMOUNT_NUMBER = 98;
  public static final int SUPER_ECONOMIQUE_AMOUNT_NUMBER = 97;
  public static final int HYBRIDE_ECONOMIQUE_AMOUNT_NUMBER = 96;
  public static final int SANS_POLLUTION_AMOUNT_NUMBER = 95;

  @Mock
  private TransactionService transactionService;
  private RevenueAssembler revenueAssembler;
  private RevenueResourceImpl resource;
  private Map<CarType, Amount> carTypeRevenues;

  @Before
  public void setUp() throws Exception {
    revenueAssembler = new RevenueAssembler();
    resource = new RevenueResourceImpl(transactionService, revenueAssembler);
    carTypeRevenues = generateCarTypesRevenues();
  }

  @Test
  public void whenGettingCarTypeTotalRevenue_shouldReturnResponseWithCorrectInfos() {
    BDDMockito.given(transactionService.getTotalCampusAccessRevenueByCarType()).willReturn(carTypeRevenues);

    CarTypeTotalRevenueResponse response = resource.getCarTypeTotalRevenue();

    double total = GOUMANDE_AMOUNT_NUMBER + ECONOMIQUE_AMOUNT_NUMBER + SUPER_ECONOMIQUE_AMOUNT_NUMBER
        + HYBRIDE_ECONOMIQUE_AMOUNT_NUMBER + SANS_POLLUTION_AMOUNT_NUMBER;
    Truth.assertThat(response.gourmande).isEqualTo(GOUMANDE_AMOUNT_NUMBER);
    Truth.assertThat(response.economique).isEqualTo(ECONOMIQUE_AMOUNT_NUMBER);
    Truth.assertThat(response.superEconomique).isEqualTo(SUPER_ECONOMIQUE_AMOUNT_NUMBER);
    Truth.assertThat(response.hybridEconomique).isEqualTo(HYBRIDE_ECONOMIQUE_AMOUNT_NUMBER);
    Truth.assertThat(response.sansPollution).isEqualTo(SANS_POLLUTION_AMOUNT_NUMBER);
    Truth.assertThat(response.total).isEqualTo(total);
  }

  @Test
  public void whenGettingTotalInfractionsRevenue_shouldReturnResponseWithCorrectInfos() {
    BDDMockito.given(transactionService.getInfractionsTotalRevenue()).willReturn(AN_AMOUNT);

    RevenueResponse infractionsTotalRevenue = resource.getInfractionsTotalRevenue();

    Truth.assertThat(infractionsTotalRevenue.revenue).isEqualTo(AN_AMOUNT_NUMBER);
  }

  @Test
  public void whenGettingTotalPassRevenue_shouldReturnResponseWithCorrectInfos() {
    BDDMockito.given(transactionService.getPassTotalRevenue()).willReturn(AN_AMOUNT);

    RevenueResponse parkingPassTotalRevenue = resource.getParkingPassTotalRevenue();

    Truth.assertThat(parkingPassTotalRevenue.revenue).isEqualTo(AN_AMOUNT_NUMBER);
  }

  @Test
  public void whenGettingTotalRevenue_shouldReturnResponseWithCorrectInfos() {
    BDDMockito.given(transactionService.getTotalCampusAccessRevenueByCarType()).willReturn(carTypeRevenues);
    BDDMockito.given(transactionService.getInfractionsTotalRevenue()).willReturn(AN_AMOUNT);
    BDDMockito.given(transactionService.getPassTotalRevenue()).willReturn(ANOTHER_AMOUNT);

    TotalRevenueResponse totalRevenue = resource.getTotalRevenue();

    double total = GOUMANDE_AMOUNT_NUMBER + ECONOMIQUE_AMOUNT_NUMBER + SUPER_ECONOMIQUE_AMOUNT_NUMBER
        + HYBRIDE_ECONOMIQUE_AMOUNT_NUMBER + SANS_POLLUTION_AMOUNT_NUMBER + AN_AMOUNT_NUMBER + ANOTHER_AMOUNT_NUMBER;
    Truth.assertThat(totalRevenue.infraction).isEqualTo(AN_AMOUNT_NUMBER);
    Truth.assertThat(totalRevenue.pass).isEqualTo(ANOTHER_AMOUNT_NUMBER);
    Truth.assertThat(totalRevenue.campusAccess.gourmande).isEqualTo(GOUMANDE_AMOUNT_NUMBER);
    Truth.assertThat(totalRevenue.campusAccess.economique).isEqualTo(ECONOMIQUE_AMOUNT_NUMBER);
    Truth.assertThat(totalRevenue.campusAccess.superEconomique).isEqualTo(SUPER_ECONOMIQUE_AMOUNT_NUMBER);
    Truth.assertThat(totalRevenue.campusAccess.hybridEconomique).isEqualTo(HYBRIDE_ECONOMIQUE_AMOUNT_NUMBER);
    Truth.assertThat(totalRevenue.campusAccess.sansPollution).isEqualTo(SANS_POLLUTION_AMOUNT_NUMBER);
    Truth.assertThat(totalRevenue.total).isEqualTo(total);
  }

  private Map<CarType, Amount> generateCarTypesRevenues() {
    Map<CarType, Amount> carTypesRevenues = new HashMap<>();

    carTypesRevenues.put(CarType.GOURMANDE, new Amount(GOUMANDE_AMOUNT_NUMBER));
    carTypesRevenues.put(CarType.ECONOMIQUE, new Amount(ECONOMIQUE_AMOUNT_NUMBER));
    carTypesRevenues.put(CarType.SUPER_ECONOMIQUE, new Amount(SUPER_ECONOMIQUE_AMOUNT_NUMBER));
    carTypesRevenues.put(CarType.HYBRIDE_ECONOMIQUE, new Amount(HYBRIDE_ECONOMIQUE_AMOUNT_NUMBER));
    carTypesRevenues.put(CarType.SANS_POLLUTION, new Amount(SANS_POLLUTION_AMOUNT_NUMBER));

    return carTypesRevenues;
  }
}