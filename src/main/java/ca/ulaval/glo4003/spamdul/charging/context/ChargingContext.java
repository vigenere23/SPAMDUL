package ca.ulaval.glo4003.spamdul.charging.context;

import ca.ulaval.glo4003.spamdul.assemblers.charging.ChargingPointAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.charging.RechargULCardAssembler;
import ca.ulaval.glo4003.spamdul.charging.api.chargingpoint.ChargingPointResource;
import ca.ulaval.glo4003.spamdul.charging.api.rechargul.RechargULResource;
import ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.ChargingPaymentService;
import ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.ChargingPointFactory;
import ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.ChargingPointIdFactory;
import ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.ChargingPointRepository;
import ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.EnoughCreditForChargingVerifier;
import ca.ulaval.glo4003.spamdul.charging.entities.rechargul.RechargULCardFactory;
import ca.ulaval.glo4003.spamdul.charging.entities.rechargul.RechargULCardIdFactory;
import ca.ulaval.glo4003.spamdul.charging.infrastructure.persistence.chargingpoint.InMemoryChargingPointRepository;
import ca.ulaval.glo4003.spamdul.charging.usecases.ChargingPointUseCase;
import ca.ulaval.glo4003.spamdul.charging.usecases.RechargULUseCase;
import ca.ulaval.glo4003.spamdul.charging.usecases.assembler.ChargingPointDtoAssembler;
import ca.ulaval.glo4003.spamdul.charging.usecases.assembler.RechargULDtoAssembler;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserRepository;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.CarFactory;
import ca.ulaval.glo4003.spamdul.shared.context.RecordPopulator;
import ca.ulaval.glo4003.spamdul.shared.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.ids.IncrementalIdGenerator;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
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
    ChargingPointUseCase chargingPointUseCase = new ChargingPointUseCase(chargingPointRepository,
                                                                         enoughCreditForChargingVerifier,
                                                                         chargingPaymentService,
                                                                         chargingPointDtoAssembler);
    RechargULUseCase rechargULUseCase = new RechargULUseCase(userRepository,
                                                             rechargULCardFactory,
                                                             rechargULDtoAssembler);

    chargingPointResource = new ChargingPointResource(chargingPointUseCase, chargingPointAssembler);
    rechargULResource = new RechargULResource(rechargULUseCase, rechargULCardAssembler);

    ChargingPointPopulator chargingPointPopulator = new ChargingPointPopulator(chargingPointFactory,
                                                                               chargingPointRepository);
    RechargULCardPopulator rechargULCardPopulator = new RechargULCardPopulator(carFactory, rechargULCardFactory,
                                                                               userRepository);

    populateChargingPoints(chargingPointPopulator);
    populateRechargULCards(rechargULCardPopulator);
  }

  protected abstract void populateChargingPoints(RecordPopulator recordPopulator);

  protected abstract void populateRechargULCards(RecordPopulator recordPopulator);

  @Override public void registerResources(InstanceMap resources) {
    resources.add(chargingPointResource);
    resources.add(rechargULResource);
  }
}
