package ca.ulaval.glo4003.spamdul.usecases.parking.pass;

import ca.ulaval.glo4003.spamdul.entity.delivery.post.DeliveryFeeCalculator;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.PassTransactionService;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZoneFeeRepository;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPassFactory;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPassSender;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public class ParkingPassService {

  private final ParkingPassFactory parkingPassFactory;
  private final ParkingPassSender parkingPassSender;
  private final PassTransactionService passTransactionService;
  private final ParkingZoneFeeRepository parkingZoneFeeRepository;
  private final DeliveryFeeCalculator deliveryFeeCalculator;
  private final UserRepository userRepository;

  public ParkingPassService(ParkingPassFactory parkingPassFactory,
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
