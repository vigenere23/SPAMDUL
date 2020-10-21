package ca.ulaval.glo4003.spamdul.usecases.infraction;

import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionInfoRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.ValidationChain;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionDto;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.usecases.transaction.TransactionService;

public class InfractionService {

  private InfractionInfoRepository infractionInfoRepository;
  private InfractionRepository infractionRepository;
  private PassRepository passRepository;
  private ValidationChain validationChain;
  private TransactionService transactionService;

  public InfractionService(InfractionInfoRepository infractionInfoRepository,
                           InfractionRepository infractionRepository,
                           PassRepository passRepository,
                           ValidationChain validationChain,
                           TransactionService transactionService) {
    this.infractionInfoRepository = infractionInfoRepository;
    this.infractionRepository = infractionRepository;
    this.passRepository = passRepository;
    this.validationChain = validationChain;
    this.transactionService = transactionService;
  }

  public Infraction validatePass(InfractionValidationDto infractionValidationDto) {
    Pass pass = passRepository.findByPassCode(infractionValidationDto.passCode);
    try {
      validationChain.validate(pass, infractionValidationDto.parkingZone, infractionValidationDto.time);
    } catch (InfractionException e) {
      Infraction infraction = infractionInfoRepository.findBy(InfractionCode.valueOf(e.getMessage()));
      infractionRepository.save(infraction);
      return infraction;
    }
    return null;
  }

  public Infraction createNoPassInfraction() {
    Infraction infraction = infractionInfoRepository.findBy(InfractionCode.valueOf("VIG_03"));
    infractionRepository.save(infraction);
    return infraction;
  }

  public Infraction createInvalidPassException() {
    Infraction infraction = infractionInfoRepository.findBy(InfractionCode.valueOf("VIG_02"));
    infractionRepository.save(infraction);
    return infraction;
  }

  public void payInfraction(InfractionPayDto infractionPayDto) {
    Infraction infraction = infractionRepository.findBy(infractionPayDto.infractionId);
    infraction.payInfraction();
    TransactionDto transactionDto = new TransactionDto();
    transactionDto.amount = infraction.getAmount();
    transactionDto.transactionType = TransactionType.INFRACTION;
    transactionService.createTransaction(transactionDto);
  }
}