package ca.ulaval.glo4003.spamdul.context.charging;

import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPoint;
import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPointFactory;
import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPointRepository;
import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingRate;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardFactory;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardRepository;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.db.charging_point.ChargingPointRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.infrastructure.db.rechargul.RechargULCardRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.charging_point.ChargingPointResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.charging_point.ChargingPointResourceImpl;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.rechargul.RechargULResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.rechargul.RechargULResourceImpl;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging.ChargingPointAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging.RechargULCardAssembler;
import ca.ulaval.glo4003.spamdul.usecases.charging.ChargingUseCase;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.util.concurrent.TimeUnit;

public class ChargingContext {

  private final ChargingPointResource chargingPointResource;
  private final RechargULResource rechargULResource;

  public ChargingContext(TransactionFactory transactionFactory, boolean populateCards) {
    ChargingPointRepository chargingPointRepository = new ChargingPointRepositoryInMemory();
    RechargULCardRepository rechargULCardRepository = new RechargULCardRepositoryInMemory();
    ChargingPointAssembler chargingPointAssembler = new ChargingPointAssembler();
    RechargULCardAssembler rechargULCardAssembler = new RechargULCardAssembler();
    ChargingUseCase chargingUseCase = new ChargingUseCase(chargingPointRepository, rechargULCardRepository);
    chargingPointResource = new ChargingPointResourceImpl(chargingUseCase, chargingPointAssembler);
    rechargULResource = new RechargULResourceImpl(chargingUseCase, rechargULCardAssembler);

    createChargingPoints(chargingPointRepository);

    if (populateCards) {
      RechargULCardFactory rechargULCardFactory = new RechargULCardFactory(transactionFactory);
      createCards(rechargULCardFactory, rechargULCardRepository);
    }
  }

  public ChargingPointResource getChargingPointResource() {
    return chargingPointResource;
  }
  
  public RechargULResource getRechargULResource() {
    return rechargULResource;
  }

  private void createChargingPoints(ChargingPointRepository chargingPointRepository) {
    final int NUMBER_OF_CHARGING_POINTS = 10;
    ChargingPointFactory chargingPointFactory = new ChargingPointFactory();
    ChargingRate chargingRate = new ChargingRate(Amount.valueOf(1), TimeUnit.HOURS);

    for (int chargingPointNumber = 0; chargingPointNumber < NUMBER_OF_CHARGING_POINTS; chargingPointNumber++) {
      ChargingPoint chargingPoint = chargingPointFactory.create(chargingRate);
      chargingPointRepository.save(chargingPoint);
    }
  }

  private void createCards(RechargULCardFactory rechargULCardFactory, RechargULCardRepository rechargULCardRepository) {
    final int NUMBER_OF_CARDS = 10;

    for (int cardNumber = 0; cardNumber < NUMBER_OF_CARDS; cardNumber++) {
      RechargULCard card = rechargULCardFactory.create();
      rechargULCardRepository.save(card);
    }
  }
}
