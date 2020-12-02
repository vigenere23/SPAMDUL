package ca.ulaval.glo4003.spamdul.context.charging;

import ca.ulaval.glo4003.spamdul.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPointFactory;
import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPointRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardFactory;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.charging_point.InMemoryChargingPointRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.rechargul.InMemoryRechargULCardRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.charging_point.ChargingPointResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.charging_point.ChargingPointResourceImpl;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.rechargul.RechargULResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.rechargul.RechargULResourceImpl;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging.ChargingPointAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging.RechargULCardAssembler;
import ca.ulaval.glo4003.spamdul.usecases.charging.ChargingPointService;
import ca.ulaval.glo4003.spamdul.usecases.charging.RechargULService;

public abstract class ChargingContext implements ResourceContext {

  protected final ChargingPointResource chargingPointResource;
  protected final RechargULResource rechargULResource;
  protected final ChargingPointFactory chargingPointFactory;
  protected final ChargingPointRepository chargingPointRepository;
  protected final RechargULCardFactory rechargULCardFactory;
  protected final RechargULCardRepository rechargULCardRepository;

  public ChargingContext(TransactionFactory transactionFactory) {
    chargingPointFactory = new ChargingPointFactory();
    chargingPointRepository = new InMemoryChargingPointRepository();
    rechargULCardFactory = new RechargULCardFactory(transactionFactory);
    rechargULCardRepository = new InMemoryRechargULCardRepository();

    ChargingPointAssembler chargingPointAssembler = new ChargingPointAssembler();
    RechargULCardAssembler rechargULCardAssembler = new RechargULCardAssembler();
    ChargingPointService chargingPointService = new ChargingPointService(chargingPointRepository,
                                                                         rechargULCardRepository);
    RechargULService rechargULService = new RechargULService(rechargULCardRepository, rechargULCardFactory);

    chargingPointResource = new ChargingPointResourceImpl(chargingPointService, chargingPointAssembler);
    rechargULResource = new RechargULResourceImpl(rechargULService, rechargULCardAssembler);

    populateChargingPoints();
    populateRechargULCards();
  }

  protected abstract void populateChargingPoints();

  protected abstract void populateRechargULCards();
}
