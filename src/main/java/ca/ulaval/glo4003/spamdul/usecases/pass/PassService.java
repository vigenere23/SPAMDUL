package ca.ulaval.glo4003.spamdul.usecases.pass;

import ca.ulaval.glo4003.spamdul.entity.account.BankRepository;
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

  public PassService(PassRepository passRepository,
                     PassFactory passFactory,
                     CampusAccessService campusAccessService,
                     PassSender passSender,
                     TransactionFactory transactionFactory,
                     BankRepository bankRepository,
                     ParkingZoneFeeRepository parkingZoneFeeRepository) {
    this.passRepository = passRepository;
    this.passFactory = passFactory;
    this.campusAccessService = campusAccessService;
    this.passSender = passSender;
    this.transactionFactory = transactionFactory;
    this.bankRepository = bankRepository;
    this.parkingZoneFeeRepository = parkingZoneFeeRepository;
  }

  public void createPass(PassDto dto) {
    Pass pass = passFactory.create(dto.parkingZone, dto.timePeriodDto);
    campusAccessService.associatePassToCampusAccess(dto.campusAccessCode, pass.getPassCode(), pass.getTimePeriod());
    passRepository.save(pass);

    TransactionDto transactionDto = new TransactionDto();
    transactionDto.transactionType = TransactionType.PASS;
    transactionDto.amount = parkingZoneFeeRepository.findBy(dto.parkingZone, dto.timePeriodDto.periodType).getFee();
    Transaction transaction = transactionFactory.create(transactionDto);
    bankRepository.getMainBankAccount().addTransaction(transaction);
    //TODO il manque l'option d'ajouter 5$ si l'option postal est choisit
    //TODO il faut resaver le bank account dans le repository apres ajouter la transaction et le faire
    //TODO dans tous les autres services

    passSender.sendPass(dto.deliveryDto, pass.getPassCode());
  }
}
