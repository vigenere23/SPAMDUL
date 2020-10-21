package ca.ulaval.glo4003.spamdul.usecases.infraction;

import ca.ulaval.glo4003.spamdul.entity.infractions.*;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionDto;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.usecases.transactions.TransactionService;

public class InfractionService {

  private final InfractionInfoRepository infractionInfoRepository;
  private final InfractionRepository infractionRepository;
  private final ValidationChain validationChain;
  private final TransactionService transactionService;
  private final InfractionFactory infractionFactory;

  public InfractionService(InfractionInfoRepository infractionInfoRepository,
                           InfractionRepository infractionRepository,
                           ValidationChain validationChain,
                           TransactionService transactionService,
                           InfractionFactory infractionFactory) {
    this.infractionInfoRepository = infractionInfoRepository;
    this.infractionRepository = infractionRepository;
    this.validationChain = validationChain;
    this.transactionService = transactionService;
    this.infractionFactory = infractionFactory;
  }

  public Infraction validatePass(PassToValidateDto passToValidateDto) {
    Infraction infraction = null;

    try {
      validationChain.validate(passToValidateDto);
    } catch (InfractionException e) {
      InfractionInfos infractionInfos = infractionInfoRepository.findBy(InfractionCode.valueOf(e.getMessage()));
      infraction = infractionFactory.create(infractionInfos);
      infractionRepository.save(infraction);
    }

    return infraction;
  }

  public void payInfraction(InfractionPayDto infractionPayDto) {
    Infraction infraction = infractionRepository.findBy(infractionPayDto.infractionId);
    infraction.pay();

    TransactionDto transactionDto = new TransactionDto();
    transactionDto.amount = infraction.getAmount();
    transactionDto.transactionType = TransactionType.INFRACTION;

    transactionService.createTransaction(transactionDto);
  }
}