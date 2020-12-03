package ca.ulaval.glo4003.spamdul.usecases.infraction;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.InfractionTransactionService;
import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionFactory;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionInfoRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionInfos;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.PassValidator;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public class InfractionService {

  private final InfractionInfoRepository infractionInfoRepository;
  private final InfractionRepository infractionRepository;
  private UserRepository userRepository;
  private final InfractionFactory infractionFactory;
  private final PassValidator firstValidationNode;
  private final AccessLevelValidator accessLevelValidator;
  private final InfractionTransactionService infractionTransactionService;

  public InfractionService(InfractionInfoRepository infractionInfoRepository,
                           InfractionRepository infractionRepository,
                           UserRepository userRepository,
                           InfractionFactory infractionFactory,
                           PassValidator firstValidationNode,
                           AccessLevelValidator accessLevelValidator,
                           InfractionTransactionService infractionTransactionService) {
    this.infractionInfoRepository = infractionInfoRepository;
    this.infractionRepository = infractionRepository;
    this.userRepository = userRepository;
    this.infractionFactory = infractionFactory;
    this.firstValidationNode = firstValidationNode;
    this.accessLevelValidator = accessLevelValidator;
    this.infractionTransactionService = infractionTransactionService;
  }

  public Infraction giveInfractionIfNotValid(PassToValidateDto passToValidateDto, TemporaryToken temporaryToken) {
    accessLevelValidator.validate(temporaryToken);

    Infraction infraction = null;

    try {
      firstValidationNode.validate(passToValidateDto);
    } catch (InfractionException e) {
      infraction = createInfraction(InfractionCode.valueOf(e.getMessage()));
    }

    if (infraction != null) {
      //TODO a tester
      User user = userRepository.findBy(passToValidateDto.licensePlate);
      user.associate(infraction);
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
    User user = infractionRepository.findBy(infractionPaymentDto.infractionId);
    Amount amountPaid = user.pay(infractionPaymentDto.infractionId);
    userRepository.save(user);
    infractionTransactionService.addRevenue(amountPaid);
  }
}
