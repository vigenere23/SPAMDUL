package ca.ulaval.glo4003.spamdul.context.pass;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryStrategyFactory;
import ca.ulaval.glo4003.spamdul.entity.delivery.email.EmailServiceFactory;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.PassTransactionService;
import ca.ulaval.glo4003.spamdul.entity.pass.PassSender;
import ca.ulaval.glo4003.spamdul.infrastructure.delivery.email.NullEmailService;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.pass.PassResourceImpl;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.CampusAccessService;
import ca.ulaval.glo4003.spamdul.usecases.pass.PassService;

public class DevPassContext extends PassContext {

  public DevPassContext(PassTransactionService passTransactionService,
                        CampusAccessService campusAccessService) {
    super(passTransactionService, campusAccessService);

    DeliveryStrategyFactory deliveryStrategyFactory = new DeliveryStrategyFactory(new EmailServiceFactory(new NullEmailService()));
    PassSender passSender = new PassSender(passDeliveryOptionsFactory, deliveryStrategyFactory);
    PassService passService = new PassService(passFactory,
                                              campusAccessService,
                                              passSender,
                                              passTransactionService,
                                              parkingZoneFeeRepository,
                                              deliveryFeeCalculator);
    passResource = new PassResourceImpl(passService, passAssembler);
  }
}
