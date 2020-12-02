package ca.ulaval.glo4003.spamdul.context.charging;

import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPaymentService;
import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPoint;
import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPointFactory;
import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPointRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
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
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.util.concurrent.TimeUnit;

public class ChargingContext {

  private final ChargingPointResource chargingPointResource;
  private final RechargULResource rechargULResource;
  private final ChargingPointExceptionMapper chargingPointExceptionMapper;
  private final RechargULExceptionMapper rechargULExceptionMapper;

  public ChargingContext(TransactionFactory transactionFactory, boolean populateCards) {
    ChargingPointRepository chargingPointRepository = new InMemoryChargingPointRepository();
    RechargULCardRepository rechargULCardRepository = new InMemoryRechargULCardRepository();
    ChargingPointAssembler chargingPointAssembler = new ChargingPointAssembler();
    RechargULCardAssembler rechargULCardAssembler = new RechargULCardAssembler();
    RechargULCardFactory rechargULCardFactory = new RechargULCardFactory(transactionFactory);
    ChargingPointService chargingPointService = new ChargingPointService(chargingPointRepository,
                                                                         rechargULCardRepository);
    RechargULService rechargULService = new RechargULService(rechargULCardRepository, rechargULCardFactory);

    chargingPointResource = new ChargingPointResourceImpl(chargingPointService, chargingPointAssembler);
    rechargULResource = new RechargULResourceImpl(rechargULService, rechargULCardAssembler);
    chargingPointExceptionMapper = new ChargingPointExceptionMapper();
    rechargULExceptionMapper = new RechargULExceptionMapper();

    createChargingPoints(chargingPointRepository);

    if (populateCards) {
      createCards(rechargULCardFactory, rechargULCardRepository);
    }
  }

  public ChargingPointResource getChargingPointResource() {
    return chargingPointResource;
  }

  public RechargULResource getRechargULResource() {
    return rechargULResource;
  }

  public ChargingPointExceptionMapper getChargingPointExceptionMapper() {
    return chargingPointExceptionMapper;
  }

  public RechargULExceptionMapper getRechargULExceptionMapper() {
    return rechargULExceptionMapper;
  }

  private void createChargingPoints(ChargingPointRepository chargingPointRepository) {
    final int NUMBER_OF_CHARGING_POINTS = 10;
    ChargingPointFactory chargingPointFactory = new ChargingPointFactory();
    ChargingPaymentService chargingPaymentService = new ChargingPaymentService(Amount.valueOf(1), TimeUnit.HOURS);

    for (int chargingPointNumber = 0; chargingPointNumber < NUMBER_OF_CHARGING_POINTS; chargingPointNumber++) {
      ChargingPoint chargingPoint = chargingPointFactory.create(chargingPaymentService);
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
