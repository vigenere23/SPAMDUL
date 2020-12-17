package ca.ulaval.glo4003.spamdul.charging.usecases;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.ChargingPaymentService;
import ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.ChargingPoint;
import ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.ChargingPointId;
import ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.ChargingPointRepository;
import ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.EnoughCreditForChargingVerifier;
import ca.ulaval.glo4003.spamdul.charging.entities.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.charging.usecases.assembler.ChargingPointDtoAssembler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChargingPointUseCaseTest {

  public static final ChargingPointId CHARGING_POINT_ID = ChargingPointId.valueOf("123");
  public static final RechargULCardId RECHARG_UL_CARD_ID = RechargULCardId.valueOf("123");
  private ChargingPointUseCase chargingPointUseCase;

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
    chargingPointUseCase = new ChargingPointUseCase(chargingPointRepository,
                                                    enoughCreditForChargingVerifier,
                                                    chargingPaymentService,
                                                    chargingPointDtoAssembler);

    when(chargingPointRepository.findBy(CHARGING_POINT_ID)).thenReturn(chargingPoint);
  }

  @Test
  public void whenGettingAllChargingPoints_shouldGetItFromRepository() {
    chargingPointUseCase.getAllChargingPoints();

    verify(chargingPointRepository).findAll();
  }

  @Test
  public void whenGettingChargingPoint_shouldGetItFromRepository() {
    chargingPointUseCase.getChargingPoint(CHARGING_POINT_ID);

    verify(chargingPointRepository).findBy(CHARGING_POINT_ID);
  }

  @Test
  public void whenActivatingChargingPointShouldActivateChagingPoint() {
    chargingPointUseCase.activateChargingPoint(CHARGING_POINT_ID, RECHARG_UL_CARD_ID);

    verify(chargingPoint).activate(enoughCreditForChargingVerifier, RECHARG_UL_CARD_ID);
    verify(chargingPointRepository).update(chargingPoint);
  }

  @Test
  public void whenStartingRecharging_shouldConnectChargingPoint() {
    chargingPointUseCase.startRecharging(CHARGING_POINT_ID);

    verify(chargingPoint).connect();
    verify(chargingPointRepository).update(chargingPoint);
  }

  @Test
  public void whenStopingRecharging_shouldDisconnectCharghingPoint() {
    chargingPointUseCase.stopRecharging(CHARGING_POINT_ID);

    verify(chargingPoint).disconnect();
    verify(chargingPointRepository).update(chargingPoint);
  }

  @Test
  public void whenDeactivating_shouldDeactivateAndPayChargingPoint() {
    chargingPointUseCase.deactivateChargingPoint(CHARGING_POINT_ID);

    verify(chargingPoint).deactivateAndPay(chargingPaymentService);
    verify(chargingPointRepository).update(chargingPoint);
  }
}
