package ca.ulaval.glo4003.spamdul.context.charging;

import ca.ulaval.glo4003.spamdul.context.Populator;
import ca.ulaval.glo4003.spamdul.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.UserRepository;
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
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging.ChargingPointExceptionMapper;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging.RechargULCardAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging.RechargULExceptionMapper;
import ca.ulaval.glo4003.spamdul.usecases.charging.ChargingPointService;
import ca.ulaval.glo4003.spamdul.usecases.charging.RechargULService;
import ca.ulaval.glo4003.spamdul.utils.InstanceMap;

public abstract class ChargingContext implements ResourceContext {

  protected final ChargingPointResource chargingPointResource;
  protected final RechargULResource rechargULResource;

  public ChargingContext(TransactionFactory transactionFactory, UserRepository userRepository) {
    ChargingPointRepository chargingPointRepository = new InMemoryChargingPointRepository();
    ChargingPointFactory chargingPointFactory = new ChargingPointFactory();
    RechargULCardFactory rechargULCardFactory = new RechargULCardFactory(transactionFactory);
    ChargingPointAssembler chargingPointAssembler = new ChargingPointAssembler();
    RechargULCardAssembler rechargULCardAssembler = new RechargULCardAssembler();
    ChargingPointService chargingPointService = new ChargingPointService(chargingPointRepository,
                                                                         userRepository);
    RechargULService rechargULService = new RechargULService(userRepository, rechargULCardFactory);

    chargingPointResource = new ChargingPointResourceImpl(chargingPointService, chargingPointAssembler);
    rechargULResource = new RechargULResourceImpl(rechargULService, rechargULCardAssembler);

    ChargingPointPopulator chargingPointPopulator = new ChargingPointPopulator(chargingPointFactory,
                                                                               chargingPointRepository);
    RechargULCardPopulator rechargULCardPopulator = new RechargULCardPopulator(rechargULCardFactory,
                                                                               userRepository);

    populateChargingPoints(chargingPointPopulator);
    populateRechargULCards(rechargULCardPopulator);
  }

  protected abstract void populateChargingPoints(Populator populator);

  protected abstract void populateRechargULCards(Populator populator);

  @Override public void registerResources(InstanceMap resources) {
    resources.add(chargingPointResource);
    resources.add(new ChargingPointExceptionMapper());
    resources.add(rechargULResource);
    resources.add(new RechargULExceptionMapper());
  }
}
