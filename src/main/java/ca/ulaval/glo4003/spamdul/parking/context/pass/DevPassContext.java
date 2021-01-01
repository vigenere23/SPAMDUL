package ca.ulaval.glo4003.spamdul.parking.context.pass;

import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.PassTransactionService;
import ca.ulaval.glo4003.spamdul.parking.api.pass.ParkingPassResource;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.DeliveryStrategyFactory;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserRepository;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingPassSender;
import ca.ulaval.glo4003.spamdul.parking.infrastructure.delivery.email.NullEmailService;
import ca.ulaval.glo4003.spamdul.parking.infrastructure.delivery.post.LoggerPostalService;
import ca.ulaval.glo4003.spamdul.parking.infrastructure.delivery.sspoffice.LoggerSSPOfficeService;
import ca.ulaval.glo4003.spamdul.parking.usecases.parkingpass.ParkingPassUseCase;

public class DevPassContext extends PassContext {

  public DevPassContext(PassTransactionService passTransactionService,
                        UserRepository userRepository) {
    DeliveryStrategyFactory deliveryStrategyFactory = new DeliveryStrategyFactory(
        new NullEmailService(),
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
