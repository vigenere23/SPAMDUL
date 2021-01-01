package ca.ulaval.glo4003.spamdul.parking.usecases.parkingpass;

import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.PassTransactionService;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.DeliveryMode;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.post.DeliveryFeeCalculator;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.User;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserRepository;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingPass;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingPassFactory;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingPassSender;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZoneFeeRepository;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.PeriodType;

public class ParkingPassUseCase {

  private final ParkingPassFactory parkingPassFactory;
  private final ParkingPassSender parkingPassSender;
  private final PassTransactionService passTransactionService;
  private final ParkingZoneFeeRepository parkingZoneFeeRepository;
  private final DeliveryFeeCalculator deliveryFeeCalculator;
  private final UserRepository userRepository;

  public ParkingPassUseCase(ParkingPassFactory parkingPassFactory,
                            ParkingPassSender parkingPassSender,
                            PassTransactionService passTransactionService,
                            ParkingZoneFeeRepository parkingZoneFeeRepository,
                            DeliveryFeeCalculator deliveryFeeCalculator,
                            UserRepository userRepository) {
    this.parkingPassFactory = parkingPassFactory;
    this.parkingPassSender = parkingPassSender;
    this.passTransactionService = passTransactionService;
    this.parkingZoneFeeRepository = parkingZoneFeeRepository;
    this.deliveryFeeCalculator = deliveryFeeCalculator;
    this.userRepository = userRepository;
  }

  public void createPass(PassCreationDto passCreationDto) {
    ParkingPass parkingPass = parkingPassFactory.create(passCreationDto.parkingZone, passCreationDto.timePeriodDto);
    User user = userRepository.findBy(passCreationDto.userId);

    user.associate(parkingPass);
    userRepository.save(user);
    addRevenue(passCreationDto.deliveryDto.deliveryMode,
               passCreationDto.parkingZone,
               passCreationDto.timePeriodDto.periodType);

    parkingPassSender.sendPass(passCreationDto.deliveryDto, parkingPass.getCode());
  }

  private void addRevenue(DeliveryMode deliveryMode, ParkingZone parkingZone, PeriodType periodType) {
    Amount deliveryFee = deliveryFeeCalculator.calculateBy(deliveryMode);
    Amount parkingZoneFee = parkingZoneFeeRepository.findBy(parkingZone, periodType);
    Amount total = deliveryFee.add(parkingZoneFee);

    passTransactionService.addRevenue(total);
  }
}
