package ca.ulaval.glo4003.spamdul.usecases.charging;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.charging.ChargingPaymentService;
import ca.ulaval.glo4003.spamdul.entity.charging.ChargingPoint;
import ca.ulaval.glo4003.spamdul.entity.charging.ChargingPointId;
import ca.ulaval.glo4003.spamdul.entity.charging.ChargingPointRepository;
import ca.ulaval.glo4003.spamdul.entity.charging.EnoughCreditForChargingVerifier;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.usecases.charging.assembler.ChargingPointDtoAssembler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChargingPointServiceTest {

  public static final ChargingPointId CHARGING_POINT_ID = ChargingPointId.valueOf("123");
  public static final RechargULCardId RECHARG_UL_CARD_ID = RechargULCardId.valueOf("123");
  private ChargingPointService chargingPointService;

  @Mock
  private ChargingPointRepository chargingPointRepository;
  @Mock
  private ChargingPaymentService chargingPaymentService;
  @Mock
  private EnoughCreditForChargingVerifier enoughCreditForChargingVerifier;
  @Mock
  private ChargingPoint chargingPoint;
  @Mock
  private ChargingPointDtoAssembler chargingPointDtoAssembler;

  @Before
  public void setUp() throws Exception {
    chargingPointService = new ChargingPointService(chargingPointRepository,
                                                    enoughCreditForChargingVerifier,
                                                    chargingPaymentService,
                                                    chargingPointDtoAssembler);

    when(chargingPointRepository.findBy(CHARGING_POINT_ID)).thenReturn(chargingPoint);
  }

  @Test
  public void whenGettingAllChargingPoints_shouldGetItFromRepository() {
    chargingPointService.getAllChargingPoints();

    verify(chargingPointRepository, times(1)).findAll();
  }

  @Test
  public void whenGettingChargingPoint_shouldGetItFromRepository() {
    chargingPointService.getChargingPoint(CHARGING_POINT_ID);

    verify(chargingPointRepository, times(1)).findBy(CHARGING_POINT_ID);
  }

  @Test
  public void whenActivatingChargingPointShouldActivateChagingPoint() {
    chargingPointService.activateChargingPoint(CHARGING_POINT_ID, RECHARG_UL_CARD_ID);

    verify(chargingPoint, times(1)).activate(enoughCreditForChargingVerifier, RECHARG_UL_CARD_ID);
    verify(chargingPointRepository, times(1)).update(chargingPoint);
  }

  @Test
  public void whenStartingRecharging_shouldConnectChargingPoint() {
    chargingPointService.startRecharging(CHARGING_POINT_ID);

    verify(chargingPoint, times(1)).connect();
    verify(chargingPointRepository, times(1)).update(chargingPoint);
  }

  @Test
  public void whenStopingRecharging_shouldDisconnectCharghingPoint() {
    chargingPointService.stopRecharging(CHARGING_POINT_ID);

    verify(chargingPoint, times(1)).disconnect();
    verify(chargingPointRepository, times(1)).update(chargingPoint);
  }

  @Test
  public void whenDeactivating_shouldDeactivateAndPayChargingPoint() {
    chargingPointService.deactivateChargingPoint(CHARGING_POINT_ID);

    verify(chargingPoint, times(1)).deactivateAndPay(chargingPaymentService);
    verify(chargingPointRepository, times(1)).update(chargingPoint);
  }
}
