package ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.CarTypeRevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.CarTypeTotalRevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.RevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.TotalRevenueResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.revenue.TransactionAssembler;
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

  public static final int AN_AMOUNT_NUMBER = 99;
  public static final Amount AN_AMOUNT = new Amount(AN_AMOUNT_NUMBER);
  public static final int ANOTHER_AMOUNT_NUMBER = 90;
  public static final Amount ANOTHER_AMOUNT = new Amount(ANOTHER_AMOUNT_NUMBER);
  public static final CarType A_CAR_TYPE = CarType.GOURMANDE;
  public static final CarType ANOTHER_CAR_TYPE = CarType.ECONOMIQUE;
  public static final int ANOTHER_ANOTHER_AMOUNT_NUMBER = 11;
  public static final Amount ANOTHER_ANOTHER_AMOUNT = new Amount(ANOTHER_ANOTHER_AMOUNT_NUMBER);

  @Mock
  private TransactionService transactionService;
  private TransactionAssembler transactionAssembler;
  private RevenueResourceImpl resource;
  private Map<CarType, Amount> carTypeRevenues;

  @Before
  public void setUp() throws Exception {
    transactionAssembler = new TransactionAssembler();
    resource = new RevenueResourceImpl(transactionService, transactionAssembler);
    carTypeRevenues = new HashMap<>();
    carTypeRevenues.put(A_CAR_TYPE, AN_AMOUNT);
    carTypeRevenues.put(ANOTHER_CAR_TYPE, ANOTHER_AMOUNT);
  }

  @Test
  public void whenGettingCampusAccessTotalRevenue_shouldReturnResponseWithCorrectInfo() {
    BDDMockito.given(transactionService.getCampusAccessTotalRevenue()).willReturn(AN_AMOUNT);

    RevenueResponse campusAccessTotalRevenue = resource.getCampusAccessTotalRevenue();

    Truth.assertThat(campusAccessTotalRevenue.revenue).isEqualTo(AN_AMOUNT_NUMBER);
  }

  @Test
  public void whenGettingCarTypeTotalRevenue_shouldReturnResponseWithCorrectInfos() {
    CarTypeRevenueResponse aCarTypeRevenueResponse = new CarTypeRevenueResponse();
    aCarTypeRevenueResponse.carType = A_CAR_TYPE;
    aCarTypeRevenueResponse.revenue = AN_AMOUNT_NUMBER;
    CarTypeRevenueResponse anotherCarTypeRevenueResponse = new CarTypeRevenueResponse();
    anotherCarTypeRevenueResponse.carType = ANOTHER_CAR_TYPE;
    anotherCarTypeRevenueResponse.revenue = ANOTHER_AMOUNT_NUMBER;
    BDDMockito.given(transactionService.getTotalCampusAccessRevenueByCarType()).willReturn(carTypeRevenues);

    CarTypeTotalRevenueResponse response = resource.getCarTypeTotalRevenue();

    Truth.assertThat(response.carTypesRevenue).contains(aCarTypeRevenueResponse);
    Truth.assertThat(response.carTypesRevenue).contains(anotherCarTypeRevenueResponse);
    Truth.assertThat(response.carTypesRevenue).hasSize(2);
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
    CarTypeRevenueResponse aCarTypeRevenueResponse = new CarTypeRevenueResponse();
    aCarTypeRevenueResponse.carType = A_CAR_TYPE;
    aCarTypeRevenueResponse.revenue = AN_AMOUNT_NUMBER;
    CarTypeRevenueResponse anotherCarTypeRevenueResponse = new CarTypeRevenueResponse();
    anotherCarTypeRevenueResponse.carType = ANOTHER_CAR_TYPE;
    anotherCarTypeRevenueResponse.revenue = ANOTHER_AMOUNT_NUMBER;
    BDDMockito.given(transactionService.getTotalCampusAccessRevenueByCarType()).willReturn(carTypeRevenues);
    BDDMockito.given(transactionService.getInfractionsTotalRevenue()).willReturn(AN_AMOUNT);
    BDDMockito.given(transactionService.getPassTotalRevenue()).willReturn(ANOTHER_AMOUNT);
    BDDMockito.given(transactionService.getCampusAccessTotalRevenue()).willReturn(ANOTHER_ANOTHER_AMOUNT);

    TotalRevenueResponse totalRevenue = resource.getTotalRevenue();

    Truth.assertThat(totalRevenue.campusAccessRevenue.revenue).isEqualTo(ANOTHER_ANOTHER_AMOUNT_NUMBER);
    Truth.assertThat(totalRevenue.infractionRevenue.revenue).isEqualTo(AN_AMOUNT_NUMBER);
    Truth.assertThat(totalRevenue.passRevenue.revenue).isEqualTo(ANOTHER_AMOUNT_NUMBER);
    Truth.assertThat(totalRevenue.carTypesRevenue).contains(aCarTypeRevenueResponse);
    Truth.assertThat(totalRevenue.carTypesRevenue).contains(anotherCarTypeRevenueResponse);
    Truth.assertThat(totalRevenue.carTypesRevenue).hasSize(2);
  }
}