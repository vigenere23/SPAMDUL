package ca.ulaval.glo4003.spamdul.usecases.pass;

import ca.ulaval.glo4003.spamdul.entity.delivery.post.DeliveryFeeCalculator;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.PassBankAccount;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZoneFeeRepository;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassFactory;
import ca.ulaval.glo4003.spamdul.entity.pass.PassSender;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.CampusAccessService;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public class PassService {

  private final PassFactory passFactory;
  private final CampusAccessService campusAccessService;
  private final PassSender passSender;
  private final PassBankAccount passBankAccount;
  private final ParkingZoneFeeRepository parkingZoneFeeRepository;
  private final DeliveryFeeCalculator deliveryFeeCalculator;

  public PassService(PassFactory passFactory,
                     CampusAccessService campusAccessService,
                     PassSender passSender,
                     PassBankAccount passBankAccount,
                     ParkingZoneFeeRepository parkingZoneFeeRepository,
                     DeliveryFeeCalculator deliveryFeeCalculator) {
    this.passFactory = passFactory;
    this.campusAccessService = campusAccessService;
    this.passSender = passSender;
    this.passBankAccount = passBankAccount;
    this.parkingZoneFeeRepository = parkingZoneFeeRepository;
    this.deliveryFeeCalculator = deliveryFeeCalculator;
  }

  public void createPass(PassDto dto) {
    Pass pass = passFactory.create(dto.parkingZone, dto.timePeriodDto);
    campusAccessService.associatePassToCampusAccess(dto.campusAccessCode, pass);

    addRevenue(dto);

    passSender.sendPass(dto.deliveryDto, pass.getPassCode());
  }

  private void addRevenue(PassDto dto) {
    Amount deliveryFee = deliveryFeeCalculator.calculateBy(dto.deliveryDto.deliveryMode).getFee();
    Amount parkingZoneFee = parkingZoneFeeRepository.findBy(dto.parkingZone, dto.timePeriodDto.periodType);
    Amount total = deliveryFee.add(parkingZoneFee);

    passBankAccount.addRevenue(total);
  }
}
