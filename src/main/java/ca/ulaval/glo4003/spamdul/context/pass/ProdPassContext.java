package ca.ulaval.glo4003.spamdul.context.pass;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryStrategyFactory;
import ca.ulaval.glo4003.spamdul.entity.delivery.email.EmailServiceFactory;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.PassTransactionService;
import ca.ulaval.glo4003.spamdul.entity.pass.PassSender;
import ca.ulaval.glo4003.spamdul.infrastructure.delivery.email.GmailEmailService;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.pass.PassResourceImpl;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.CampusAccessService;
import ca.ulaval.glo4003.spamdul.usecases.pass.PassService;

public class ProdPassContext extends PassContext {

  public ProdPassContext(PassTransactionService passTransactionService,
                         UserRepository userRepository) {
    DeliveryStrategyFactory deliveryStrategyFactory = new DeliveryStrategyFactory(new EmailServiceFactory(new GmailEmailService()));
    PassSender passSender = new PassSender(passDeliveryOptionsFactory, deliveryStrategyFactory);
    PassService passService = new PassService(passFactory,
                                              passSender,
                                              passTransactionService,
                                              parkingZoneFeeRepository,
                                              deliveryFeeCalculator,
                                              userRepository);
    passResource = new PassResourceImpl(passService, passAssembler);
  }
}
