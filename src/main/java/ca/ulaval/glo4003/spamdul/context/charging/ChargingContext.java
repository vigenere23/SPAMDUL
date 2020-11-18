package ca.ulaval.glo4003.spamdul.context.charging;

import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPointRepository;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.charging_point.ChargingPointRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.infrastructure.db.rechargul.RechargULCardRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.charging_point.ChargingPointResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.charging_point.ChargingPointResourceImpl;
import ca.ulaval.glo4003.spamdul.usecases.charging.ChargingUseCase;

public class ChargingContext {

  private final ChargingPointResource chargingPointResource;

  public ChargingContext() {
    ChargingPointRepository chargingPointRepository = new ChargingPointRepositoryInMemory();
    RechargULCardRepository rechargULCardRepository = new RechargULCardRepositoryInMemory();
    ChargingUseCase chargingUseCase = new ChargingUseCase(chargingPointRepository, rechargULCardRepository);
    chargingPointResource = new ChargingPointResourceImpl(chargingUseCase);
  }

  public ChargingPointResource getChargingPointResource() {
    return chargingPointResource;
  }
}
