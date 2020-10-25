package ca.ulaval.glo4003.spamdul.usecases.infraction;

import ca.ulaval.glo4003.spamdul.entity.account.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionFactory;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionInfoRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionInfos;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.PassValidator;
import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionDto;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.usecases.transactions.TransactionService;

public class InfractionService {

  private final InfractionInfoRepository infractionInfoRepository;
  private final InfractionRepository infractionRepository;
  private final InfractionFactory infractionFactory;
  private final PassValidator firstValidationNode;
  private TransactionFactory transactionFactory;
  private BankRepository bankRepository;

  public InfractionService(InfractionInfoRepository infractionInfoRepository,
                           InfractionRepository infractionRepository,
                           InfractionFactory infractionFactory,
                           PassValidator firstValidationNode,
                           TransactionFactory transactionFactory,
                           BankRepository bankRepository) {
    this.infractionInfoRepository = infractionInfoRepository;
    this.infractionRepository = infractionRepository;
    this.infractionFactory = infractionFactory;
    this.firstValidationNode = firstValidationNode;
    this.transactionFactory = transactionFactory;
    this.bankRepository = bankRepository;
  }

  public Infraction giveInfractionIfNotValid(PassToValidateDto passToValidateDto) {
    Infraction infraction = null;

    try {
      firstValidationNode.validate(passToValidateDto);
    } catch (InfractionException e) {
      infraction = createInfraction(InfractionCode.valueOf(e.getMessage()));
    }

    return infraction;
  }

  private Infraction createInfraction(InfractionCode infractionCode) {
    InfractionInfos infractionInfos = infractionInfoRepository.findBy(infractionCode);
    Infraction infraction = infractionFactory.create(infractionInfos);
    infractionRepository.save(infraction);

    return infraction;
  }

  public void payInfraction(InfractionPaymentDto infractionPaymentDto) {
    Infraction infraction = infractionRepository.findBy(infractionPaymentDto.infractionId);

    TransactionDto transactionDto = new TransactionDto();
    transactionDto.amount = infraction.getAmount();
    transactionDto.transactionType = TransactionType.INFRACTION;
    Transaction transaction = transactionFactory.create(transactionDto);
    bankRepository.getMainBankAccount().addTransaction(transaction);

    infraction.pay();
  }
}