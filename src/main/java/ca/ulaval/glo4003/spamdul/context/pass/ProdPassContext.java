package ca.ulaval.glo4003.spamdul.context.pass;

import ca.ulaval.glo4003.spamdul.api.pass.ParkingPassResource;
import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryStrategyFactory;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.PassTransactionService;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPassSender;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.delivery.email.GmailEmailService;
import ca.ulaval.glo4003.spamdul.infrastructure.delivery.post.LoggerPostalService;
import ca.ulaval.glo4003.spamdul.infrastructure.delivery.sspoffice.LoggerSSPOfficeService;
import ca.ulaval.glo4003.spamdul.usecases.parking.pass.ParkingPassUseCase;

public class ProdPassContext extends PassContext {

  public ProdPassContext(PassTransactionService passTransactionService,
                         UserRepository userRepository) {
    DeliveryStrategyFactory deliveryStrategyFactory = new DeliveryStrategyFactory(
        new GmailEmailService(),
        new LoggerPostalService(),
        new LoggerSSPOfficeService());
    ParkingPassSender parkingPassSender = new ParkingPassSender(parkingPassDeliveryOptionsFactory,
                                                                deliveryStrategyFactory);
    ParkingPassUseCase parkingPassUseCase = new ParkingPassUseCase(parkingPassFactory,
                                                                   parkingPassSender,
                                                                   passTransactionService,
                                                                   parkingZoneFeeRepository,
                                                                   deliveryFeeCalculator,
                                                                   userRepository);
    parkingPassResource = new ParkingPassResource(parkingPassUseCase, passAssembler);
  }
}
