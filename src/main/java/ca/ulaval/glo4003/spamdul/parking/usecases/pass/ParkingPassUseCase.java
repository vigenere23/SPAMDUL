package ca.ulaval.glo4003.spamdul.parking.usecases.pass;

import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.PassTransactionService;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.post.DeliveryFeeCalculator;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.User;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserRepository;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingPass;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingPassFactory;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingPassSender;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZoneFeeRepository;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

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

  public void createPass(PassDto dto) {
    ParkingPass parkingPass = parkingPassFactory.create(dto.parkingZone, dto.timePeriodDto);
    User user = userRepository.findBy(dto.userId);

    user.associate(parkingPass);
    userRepository.save(user);
    addRevenue(dto);

    parkingPassSender.sendPass(dto.deliveryDto, parkingPass.getCode());
  }

  private void addRevenue(PassDto dto) {
    Amount deliveryFee = deliveryFeeCalculator.calculateBy(dto.deliveryDto.deliveryMode);
    Amount parkingZoneFee = parkingZoneFeeRepository.findBy(dto.parkingZone, dto.timePeriodDto.periodType);
    Amount total = deliveryFee.add(parkingZoneFee);

    passTransactionService.addRevenue(total);
  }
}
