package ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue;

import static org.mockito.Matchers.any;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.CarTypeTotalRevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.RevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.TotalRevenueResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.revenue.RevenueAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.revenue.TransactionQueryAssembler;
import ca.ulaval.glo4003.spamdul.usecases.transactions.TransactionService;
import ca.ulaval.glo4003.spamdul.usecases.transactions.dto.TransactionQueryDto;
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

  public static final int AMOUNT_VALUE_1 = 9;
  public static final int AMOUNT_VALUE_2 = 8;
  public static final Amount AMOUNT_1 = Amount.valueOf(AMOUNT_VALUE_1);
  public static final Amount AMOUNT_2 = Amount.valueOf(AMOUNT_VALUE_2);
  public static final int GOUMANDE_AMOUNT_NUMBER = 99;
  public static final int ECONOMIQUE_AMOUNT_NUMBER = 98;
  public static final int SUPER_ECONOMIQUE_AMOUNT_NUMBER = 97;
  public static final int HYBRIDE_ECONOMIQUE_AMOUNT_NUMBER = 96;
  public static final int SANS_POLLUTION_AMOUNT_NUMBER = 95;
  public static final TransactionQueryDto A_TRANSACTION_QUERY_DTO = new TransactionQueryDto();

  @Mock
  private TransactionService transactionService;
  @Mock
  private TransactionQueryAssembler transactionQueryAssembler;

  private RevenueAssembler revenueAssembler;
  private RevenueResourceImpl resource;
  private Map<CarType, Amount> carTypeRevenues;

  @Before
  public void setUp() throws Exception {
    revenueAssembler = new RevenueAssembler();
    resource = new RevenueResourceImpl(transactionService, transactionQueryAssembler, revenueAssembler);
    carTypeRevenues = generateCarTypesRevenues();
    BDDMockito.given(transactionQueryAssembler.fromValues(any(), any()))
              .willReturn(A_TRANSACTION_QUERY_DTO);
  }

  @Test
  public void whenGettingCarTypeTotalRevenue_shouldReturnResponseWithCorrectInfos() {
    BDDMockito.given(transactionService.getTotalCampusAccessRevenueByCarType(A_TRANSACTION_QUERY_DTO)).willReturn(
        carTypeRevenues);

    CarTypeTotalRevenueResponse response = resource.getCarTypeTotalRevenue(null, null);

    double total = GOUMANDE_AMOUNT_NUMBER + ECONOMIQUE_AMOUNT_NUMBER + SUPER_ECONOMIQUE_AMOUNT_NUMBER
        + HYBRIDE_ECONOMIQUE_AMOUNT_NUMBER + SANS_POLLUTION_AMOUNT_NUMBER;
    Truth.assertThat(response.byCarType.get(CarType.GOURMANDE)).isEqualTo(GOUMANDE_AMOUNT_NUMBER);
    Truth.assertThat(response.byCarType.get(CarType.ECONOMIQUE)).isEqualTo(ECONOMIQUE_AMOUNT_NUMBER);
    Truth.assertThat(response.byCarType.get(CarType.SUPER_ECONOMIQUE)).isEqualTo(SUPER_ECONOMIQUE_AMOUNT_NUMBER);
    Truth.assertThat(response.byCarType.get(CarType.HYBRIDE_ECONOMIQUE)).isEqualTo(HYBRIDE_ECONOMIQUE_AMOUNT_NUMBER);
    Truth.assertThat(response.byCarType.get(CarType.SANS_POLLUTION)).isEqualTo(SANS_POLLUTION_AMOUNT_NUMBER);
    Truth.assertThat(response.total).isEqualTo(total);
  }

  @Test
  public void whenGettingTotalInfractionsRevenue_shouldReturnResponseWithCorrectInfos() {
    BDDMockito.given(transactionService.getInfractionsTotalRevenue(A_TRANSACTION_QUERY_DTO)).willReturn(AMOUNT_1);

    RevenueResponse infractionsTotalRevenue = resource.getInfractionsTotalRevenue(null, null);

    Truth.assertThat(infractionsTotalRevenue.revenue).isEqualTo(AMOUNT_VALUE_1);
  }

  @Test
  public void whenGettingTotalPassRevenue_shouldReturnResponseWithCorrectInfos() {
    BDDMockito.given(transactionService.getPassTotalRevenue(A_TRANSACTION_QUERY_DTO)).willReturn(AMOUNT_1);

    RevenueResponse parkingPassTotalRevenue = resource.getParkingPassTotalRevenue(null, null);

    Truth.assertThat(parkingPassTotalRevenue.revenue).isEqualTo(AMOUNT_VALUE_1);
  }

  @Test
  public void whenGettingTotalRevenue_shouldReturnResponseWithCorrectInfos() {
    BDDMockito.given(transactionService.getTotalCampusAccessRevenueByCarType(A_TRANSACTION_QUERY_DTO)).willReturn(
        carTypeRevenues);
    BDDMockito.given(transactionService.getInfractionsTotalRevenue(A_TRANSACTION_QUERY_DTO)).willReturn(AMOUNT_1);
    BDDMockito.given(transactionService.getPassTotalRevenue(A_TRANSACTION_QUERY_DTO)).willReturn(AMOUNT_2);

    TotalRevenueResponse totalRevenue = resource.getTotalRevenue(null, null);

    double total = GOUMANDE_AMOUNT_NUMBER + ECONOMIQUE_AMOUNT_NUMBER + SUPER_ECONOMIQUE_AMOUNT_NUMBER
        + HYBRIDE_ECONOMIQUE_AMOUNT_NUMBER + SANS_POLLUTION_AMOUNT_NUMBER + AMOUNT_VALUE_1 + AMOUNT_VALUE_2;
    Truth.assertThat(totalRevenue.infraction).isEqualTo(AMOUNT_VALUE_1);
    Truth.assertThat(totalRevenue.pass).isEqualTo(AMOUNT_VALUE_2);
    Truth.assertThat(totalRevenue.campusAccess.byCarType.get(CarType.GOURMANDE)).isEqualTo(GOUMANDE_AMOUNT_NUMBER);
    Truth.assertThat(totalRevenue.campusAccess.byCarType.get(CarType.ECONOMIQUE)).isEqualTo(ECONOMIQUE_AMOUNT_NUMBER);
    Truth.assertThat(totalRevenue.campusAccess.byCarType.get(CarType.SUPER_ECONOMIQUE)).isEqualTo(
        SUPER_ECONOMIQUE_AMOUNT_NUMBER);
    Truth.assertThat(totalRevenue.campusAccess.byCarType.get(CarType.HYBRIDE_ECONOMIQUE)).isEqualTo(
        HYBRIDE_ECONOMIQUE_AMOUNT_NUMBER);
    Truth.assertThat(totalRevenue.campusAccess.byCarType.get(CarType.SANS_POLLUTION)).isEqualTo(
        SANS_POLLUTION_AMOUNT_NUMBER);
    Truth.assertThat(totalRevenue.total).isEqualTo(total);
  }

  private Map<CarType, Amount> generateCarTypesRevenues() {
    Map<CarType, Amount> carTypesRevenues = new HashMap<>();

    carTypesRevenues.put(CarType.GOURMANDE, Amount.valueOf(GOUMANDE_AMOUNT_NUMBER));
    carTypesRevenues.put(CarType.ECONOMIQUE, Amount.valueOf(ECONOMIQUE_AMOUNT_NUMBER));
    carTypesRevenues.put(CarType.SUPER_ECONOMIQUE, Amount.valueOf(SUPER_ECONOMIQUE_AMOUNT_NUMBER));
    carTypesRevenues.put(CarType.HYBRIDE_ECONOMIQUE, Amount.valueOf(HYBRIDE_ECONOMIQUE_AMOUNT_NUMBER));
    carTypesRevenues.put(CarType.SANS_POLLUTION, Amount.valueOf(SANS_POLLUTION_AMOUNT_NUMBER));

    return carTypesRevenues;
  }
}