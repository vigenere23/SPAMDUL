package ca.ulaval.glo4003.spamdul.usecases.pass;

import ca.ulaval.glo4003.spamdul.entity.account.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.account.MainBankAccount;
import ca.ulaval.glo4003.spamdul.entity.delivery.post.DeliveryFeeCalculator;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZoneFeeRepository;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassFactory;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.pass.PassSender;
import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionDto;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.CampusAccessService;

public class PassService {

  private final PassRepository passRepository;
  private final PassFactory passFactory;
  private final CampusAccessService campusAccessService;
  private final PassSender passSender;
  private TransactionFactory transactionFactory;
  private BankRepository bankRepository;
  private ParkingZoneFeeRepository parkingZoneFeeRepository;
  private DeliveryFeeCalculator deliveryFeeCalculator;

  public PassService(PassRepository passRepository,
                     PassFactory passFactory,
                     CampusAccessService campusAccessService,
                     PassSender passSender,
                     TransactionFactory transactionFactory,
                     BankRepository bankRepository,
                     ParkingZoneFeeRepository parkingZoneFeeRepository,
                     DeliveryFeeCalculator postFeeRepository) {
    this.passRepository = passRepository;
    this.passFactory = passFactory;
    this.campusAccessService = campusAccessService;
    this.passSender = passSender;
    this.transactionFactory = transactionFactory;
    this.bankRepository = bankRepository;
    this.parkingZoneFeeRepository = parkingZoneFeeRepository;
    this.deliveryFeeCalculator = postFeeRepository;
  }

  public void createPass(PassDto dto) {
    Pass pass = passFactory.create(dto.parkingZone, dto.timePeriodDto);
    campusAccessService.associatePassToCampusAccess(dto.campusAccessCode, pass.getPassCode(), pass.getTimePeriod());
    passRepository.save(pass);

    TransactionDto transactionDto = new TransactionDto();
    transactionDto.transactionType = TransactionType.PASS;

    double deliveryFee = deliveryFeeCalculator.calculateBy(dto.deliveryDto.deliveryMode).getFee();
    double parkingZoneFee = parkingZoneFeeRepository.findBy(dto.parkingZone, dto.timePeriodDto.periodType).getFee();
    transactionDto.amount = parkingZoneFee + deliveryFee;

    Transaction transaction = transactionFactory.create(transactionDto);
    MainBankAccount mainBankAccount = bankRepository.getMainBankAccount();
    mainBankAccount.addTransaction(transaction);
    bankRepository.save(mainBankAccount);

    passSender.sendPass(dto.deliveryDto, pass.getPassCode());
  }
}
