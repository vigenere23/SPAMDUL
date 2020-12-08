package ca.ulaval.glo4003.spamdul.infrastructure.db.charging;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;

import ca.ulaval.glo4003.spamdul.entity.charging.ChargingPoint;
import ca.ulaval.glo4003.spamdul.entity.charging.ChargingPointId;
import ca.ulaval.glo4003.spamdul.entity.charging.EnoughCreditForChargingVerifier;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InMemoryChargingPointRepositoryTest {

  public static final ChargingPointId CHARGING_POINT_ID = ChargingPointId.valueOf("123");
  public static final ChargingPointId ANOTHER_CHARGING_POINT_ID = ChargingPointId.valueOf("345");
  public static final ChargingPoint anotherChargingPoint = new ChargingPoint(ANOTHER_CHARGING_POINT_ID);
  private InMemoryChargingPointRepository repository;
  private ChargingPoint chargingPoint;


  @Before
  public void setUp() throws Exception {
    chargingPoint = new ChargingPoint(CHARGING_POINT_ID);
    repository = new InMemoryChargingPointRepository();
  }

  @Test
  public void whenSavingChargingPoint_chargingPointShouldBeSaved() {
    repository.save(chargingPoint);

    assertThat(repository.findBy(CHARGING_POINT_ID)).isEqualTo(chargingPoint);
  }

  @Test
  public void givenSavedChargingPoint_whenFindingById_shouldReturnRightChagingPoint() {
    repository.save(chargingPoint);
    repository.save(anotherChargingPoint);

    ChargingPoint chargingPoint = repository.findBy(CHARGING_POINT_ID);

    assertThat(chargingPoint).isEqualTo(this.chargingPoint);
  }

  @Test
  public void whenFindAllChargingPoints_shouldReturnAllSavedChargingPoints() {
    repository.save(chargingPoint);
    repository.save(anotherChargingPoint);

    List<ChargingPoint> chargingPoints = repository.findAll();

    List<ChargingPoint> expectedChargingPoints = new ArrayList<>();
    expectedChargingPoints.add(chargingPoint);
    expectedChargingPoints.add(anotherChargingPoint);
    assertThat(chargingPoints).containsExactlyElementsIn(expectedChargingPoints);
  }

  @Test
  public void whenUpdatingChargingPoint_shouldUpdateChargingPoint() {
    String initialState = chargingPoint.getState();
    repository.save(chargingPoint);
    ChargingPoint chargingPoint = repository.findBy(CHARGING_POINT_ID);
    chargingPoint.activate(mock(EnoughCreditForChargingVerifier.class), mock(RechargULCardId.class));

    repository.update(chargingPoint);

    assertThat(repository.findBy(CHARGING_POINT_ID).getState()).isNotEqualTo(initialState);
  }
}
