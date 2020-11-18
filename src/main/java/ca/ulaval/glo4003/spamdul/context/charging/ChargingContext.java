package ca.ulaval.glo4003.spamdul.context.charging;

import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPoint;
import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPointFactory;
import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPointRepository;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.charging_point.ChargingPointRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.infrastructure.db.rechargul.RechargULCardRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.charging_point.ChargingPointResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.charging_point.ChargingPointResourceImpl;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging.ChargingPointAssembler;
import ca.ulaval.glo4003.spamdul.usecases.charging.ChargingUseCase;

public class ChargingContext {

  private final ChargingPointResource chargingPointResource;

  public ChargingContext() {
    ChargingPointRepository chargingPointRepository = new ChargingPointRepositoryInMemory();
    RechargULCardRepository rechargULCardRepository = new RechargULCardRepositoryInMemory();
    ChargingPointAssembler chargingPointAssembler = new ChargingPointAssembler();
    ChargingUseCase chargingUseCase = new ChargingUseCase(chargingPointRepository, rechargULCardRepository);
    chargingPointResource = new ChargingPointResourceImpl(chargingUseCase, chargingPointAssembler);

    createChargingPoints(chargingPointRepository);
  }

  public ChargingPointResource getChargingPointResource() {
    return chargingPointResource;
  }

  private void createChargingPoints(ChargingPointRepository chargingPointRepository) {
    final int NUMBER_OF_CHARGING_POINTS = 10;
    ChargingPointFactory chargingPointFactory = new ChargingPointFactory();

    for (int chargingPointNumber = 0; chargingPointNumber < NUMBER_OF_CHARGING_POINTS; chargingPointNumber++) {
      ChargingPoint chargingPoint = chargingPointFactory.create();
      chargingPointRepository.save(chargingPoint);
    }
  }
}
