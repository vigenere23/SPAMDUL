package ca.ulaval.glo4003.spamdul.context.charging;

import ca.ulaval.glo4003.spamdul.context.Populator;
import ca.ulaval.glo4003.spamdul.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.entity.charging.ChargingPaymentService;
import ca.ulaval.glo4003.spamdul.entity.charging.ChargingPointFactory;
import ca.ulaval.glo4003.spamdul.entity.charging.ChargingPointIdFactory;
import ca.ulaval.glo4003.spamdul.entity.charging.ChargingPointRepository;
import ca.ulaval.glo4003.spamdul.entity.charging.EnoughCreditForChargingVerifier;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardFactory;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardIdFactory;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.user.car.CarFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.db.charging.InMemoryChargingPointRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.ids.IncrementalIdGenerator;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging.ChargingPointAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging.RechargULCardAssembler;
import ca.ulaval.glo4003.spamdul.shared.amount.Amount;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import ca.ulaval.glo4003.spamdul.ui.charging.ChargingPointResource;
import ca.ulaval.glo4003.spamdul.ui.rechargul.RechargULResource;
import ca.ulaval.glo4003.spamdul.usecases.charging.ChargingPointService;
import ca.ulaval.glo4003.spamdul.usecases.charging.RechargULService;
import ca.ulaval.glo4003.spamdul.usecases.charging.assembler.ChargingPointDtoAssembler;
import ca.ulaval.glo4003.spamdul.usecases.charging.assembler.RechargULDtoAssembler;
import java.util.concurrent.TimeUnit;

public abstract class ChargingContext implements ResourceContext {

  protected final ChargingPointResource chargingPointResource;
  protected final RechargULResource rechargULResource;

  public ChargingContext(TransactionFactory transactionFactory,
                         UserRepository userRepository,
                         CarFactory carFactory) {
    ChargingPointRepository chargingPointRepository = new InMemoryChargingPointRepository();
    ChargingPointIdFactory chargingPointIdFactory = new ChargingPointIdFactory(new IncrementalIdGenerator());
    ChargingPointFactory chargingPointFactory = new ChargingPointFactory(chargingPointIdFactory);
    RechargULCardIdFactory rechargULCardIdFactory = new RechargULCardIdFactory(new IncrementalIdGenerator());
    RechargULCardFactory rechargULCardFactory = new RechargULCardFactory(rechargULCardIdFactory, transactionFactory);
    ChargingPointAssembler chargingPointAssembler = new ChargingPointAssembler();
    RechargULCardAssembler rechargULCardAssembler = new RechargULCardAssembler();
    ChargingPaymentService chargingPaymentService = new ChargingPaymentService(
        Amount.valueOf(1),
        TimeUnit.HOURS,
        userRepository);
    ChargingPointDtoAssembler chargingPointDtoAssembler = new ChargingPointDtoAssembler();
    RechargULDtoAssembler rechargULDtoAssembler = new RechargULDtoAssembler();
    EnoughCreditForChargingVerifier enoughCreditForChargingVerifier = new EnoughCreditForChargingVerifier(userRepository);
    ChargingPointService chargingPointService = new ChargingPointService(chargingPointRepository,
                                                                         enoughCreditForChargingVerifier,
                                                                         chargingPaymentService,
                                                                         chargingPointDtoAssembler);
    RechargULService rechargULService = new RechargULService(userRepository,
                                                             rechargULCardFactory,
                                                             rechargULDtoAssembler);

    chargingPointResource = new ChargingPointResource(chargingPointService, chargingPointAssembler);
    rechargULResource = new RechargULResource(rechargULService, rechargULCardAssembler);

    ChargingPointPopulator chargingPointPopulator = new ChargingPointPopulator(chargingPointFactory,
                                                                               chargingPointRepository);
    RechargULCardPopulator rechargULCardPopulator = new RechargULCardPopulator(carFactory, rechargULCardFactory,
                                                                               userRepository);

    populateChargingPoints(chargingPointPopulator);
    populateRechargULCards(rechargULCardPopulator);
  }

  protected abstract void populateChargingPoints(Populator populator);

  protected abstract void populateRechargULCards(Populator populator);

  @Override public void registerResources(InstanceMap resources) {
    resources.add(chargingPointResource);
    resources.add(rechargULResource);
  }
}
