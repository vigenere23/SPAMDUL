package ca.ulaval.glo4003.spamdul.usecases.infraction;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.InfractionTransactionService;
import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionFactory;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionInfoRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionInfos;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.PassValidator;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public class InfractionService {

  private final InfractionInfoRepository infractionInfoRepository;
  private UserRepository userRepository;
  private final InfractionFactory infractionFactory;
  private final PassValidator firstValidationNode;
  private final AccessLevelValidator accessLevelValidator;
  private final InfractionTransactionService infractionTransactionService;

  public InfractionService(InfractionInfoRepository infractionInfoRepository,
                           UserRepository userRepository,
                           InfractionFactory infractionFactory,
                           PassValidator firstValidationNode,
                           AccessLevelValidator accessLevelValidator,
                           InfractionTransactionService infractionTransactionService) {
    this.infractionInfoRepository = infractionInfoRepository;
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
      userRepository.save(user);
    }

    return infraction;
  }

  private Infraction createInfraction(InfractionCode infractionCode) {
    InfractionInfos infractionInfos = infractionInfoRepository.findBy(infractionCode);

    return infractionFactory.create(infractionInfos);
  }

  public void payInfraction(InfractionPaymentDto infractionPaymentDto) {
    try {
      User user = userRepository.findBy(infractionPaymentDto.infractionId);
      Amount amountPaid = user.pay(infractionPaymentDto.infractionId);

      userRepository.save(user);
      infractionTransactionService.addRevenue(amountPaid);
    } catch (UserNotFoundException e) {
      throw new InfractionNotFoundException();
    }
  }
}
