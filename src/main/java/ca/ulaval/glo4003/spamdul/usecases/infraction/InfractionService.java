package ca.ulaval.glo4003.spamdul.usecases.infraction;

import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.entity.bank.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.bank.MainBankAccount;
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

public class InfractionService {

  private final InfractionInfoRepository infractionInfoRepository;
  private final InfractionRepository infractionRepository;
  private final InfractionFactory infractionFactory;
  private final PassValidator firstValidationNode;
  private final TransactionFactory transactionFactory;
  private final BankRepository bankRepository;
  private final AccessLevelValidator accessLevelValidator;

  public InfractionService(InfractionInfoRepository infractionInfoRepository,
                           InfractionRepository infractionRepository,
                           InfractionFactory infractionFactory,
                           PassValidator firstValidationNode,
                           TransactionFactory transactionFactory,
                           BankRepository bankRepository,
                           AccessLevelValidator accessLevelValidator) {
    this.infractionInfoRepository = infractionInfoRepository;
    this.infractionRepository = infractionRepository;
    this.infractionFactory = infractionFactory;
    this.firstValidationNode = firstValidationNode;
    this.transactionFactory = transactionFactory;
    this.bankRepository = bankRepository;
    this.accessLevelValidator = accessLevelValidator;
  }

  public Infraction giveInfractionIfNotValid(PassToValidateDto passToValidateDto, TemporaryToken temporaryToken) {
    accessLevelValidator.validate(temporaryToken);

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
    MainBankAccount mainBankAccount = bankRepository.getMainBankAccount();
    mainBankAccount.addTransaction(transaction);
    bankRepository.save(mainBankAccount);

    infraction.pay();
  }
}
