package ca.ulaval.glo4003.spamdul.context.pass;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryStrategyFactory;
import ca.ulaval.glo4003.spamdul.entity.delivery.email.EmailServiceFactory;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.PassTransactionService;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPassSender;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.delivery.email.NullEmailService;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.pass.PassResourceImpl;
import ca.ulaval.glo4003.spamdul.usecases.parking.pass.ParkingPassService;

public class DevPassContext extends PassContext {

  public DevPassContext(PassTransactionService passTransactionService,
                        UserRepository userRepository) {
    DeliveryStrategyFactory deliveryStrategyFactory = new DeliveryStrategyFactory(new EmailServiceFactory(new NullEmailService()));
    ParkingPassSender parkingPassSender = new ParkingPassSender(parkingPassDeliveryOptionsFactory, deliveryStrategyFactory);
    ParkingPassService parkingPassService = new ParkingPassService(parkingPassFactory,
                                                                   parkingPassSender,
                                                                   passTransactionService,
                                                                   parkingZoneFeeRepository,
                                                                   deliveryFeeCalculator,
                                                                   userRepository);
    passResource = new PassResourceImpl(parkingPassService, passAssembler);
  }
}
