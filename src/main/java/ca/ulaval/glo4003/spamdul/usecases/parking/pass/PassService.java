package ca.ulaval.glo4003.spamdul.usecases.parking.pass;

import ca.ulaval.glo4003.spamdul.entity.delivery.post.DeliveryFeeCalculator;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.PassTransactionService;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZoneFeeRepository;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassFactory;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassSender;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public class PassService {

  private final PassFactory passFactory;
  private final PassSender passSender;
  private final PassTransactionService passTransactionService;
  private final ParkingZoneFeeRepository parkingZoneFeeRepository;
  private final DeliveryFeeCalculator deliveryFeeCalculator;
  private final UserRepository userRepository;

  public PassService(PassFactory passFactory,
                     PassSender passSender,
                     PassTransactionService passTransactionService,
                     ParkingZoneFeeRepository parkingZoneFeeRepository,
                     DeliveryFeeCalculator deliveryFeeCalculator,
                     UserRepository userRepository) {
    this.passFactory = passFactory;
    this.passSender = passSender;
    this.passTransactionService = passTransactionService;
    this.parkingZoneFeeRepository = parkingZoneFeeRepository;
    this.deliveryFeeCalculator = deliveryFeeCalculator;
    this.userRepository = userRepository;
  }

  public void createPass(PassDto dto) {
    Pass pass = passFactory.create(dto.parkingZone, dto.timePeriodDto);
    User user = userRepository.findBy(dto.userId);

    user.associate(pass);
    userRepository.save(user);
    addRevenue(dto);

    passSender.sendPass(dto.deliveryDto, pass.getPassCode());
  }

  private void addRevenue(PassDto dto) {
    Amount deliveryFee = deliveryFeeCalculator.calculateBy(dto.deliveryDto.deliveryMode);
    Amount parkingZoneFee = parkingZoneFeeRepository.findBy(dto.parkingZone, dto.timePeriodDto.periodType);
    Amount total = deliveryFee.add(parkingZoneFee);

    passTransactionService.addRevenue(total);
  }
}
