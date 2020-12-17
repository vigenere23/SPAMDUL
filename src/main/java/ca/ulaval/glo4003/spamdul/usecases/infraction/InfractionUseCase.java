package ca.ulaval.glo4003.spamdul.usecases.infraction;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.InfractionTransactionService;
import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionFactory;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionInfoRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionInfosDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.CarParkingPassValidator;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.spamdul.shared.amount.Amount;
import ca.ulaval.glo4003.spamdul.usecases.infraction.dto.InfractionDto;

public class InfractionUseCase {

  private final InfractionInfoRepository infractionInfoRepository;
  private final UserRepository userRepository;
  private final InfractionFactory infractionFactory;
  private final CarParkingPassValidator firstValidationNode;
  private final AccessLevelValidator accessLevelValidator;
  private final InfractionTransactionService infractionTransactionService;
  private final InfractionDtoAssembler infractionDtoAssembler;

  public InfractionUseCase(InfractionInfoRepository infractionInfoRepository,
                           UserRepository userRepository,
                           InfractionFactory infractionFactory,
                           CarParkingPassValidator firstValidationNode,
                           AccessLevelValidator accessLevelValidator,
                           InfractionTransactionService infractionTransactionService,
                           InfractionDtoAssembler infractionDtoAssembler) {
    this.infractionInfoRepository = infractionInfoRepository;
    this.userRepository = userRepository;
    this.infractionFactory = infractionFactory;
    this.firstValidationNode = firstValidationNode;
    this.accessLevelValidator = accessLevelValidator;
    this.infractionTransactionService = infractionTransactionService;
    this.infractionDtoAssembler = infractionDtoAssembler;
  }

  public InfractionDto giveInfractionIfNotValid(PassToValidateDto passToValidateDto, TemporaryToken temporaryToken) {
    accessLevelValidator.validate(temporaryToken);

    Infraction infraction = null;

    try {
      firstValidationNode.validate(passToValidateDto);
    } catch (InfractionException e) {
      infraction = createInfraction(InfractionCode.valueOf(e.getMessage()));
    }

    if (infraction != null) {
      User user = userRepository.findBy(passToValidateDto.licensePlate);
      user.associate(infraction);
      userRepository.save(user);
    }

    return infractionDtoAssembler.toDto(infraction);
  }

  private Infraction createInfraction(InfractionCode infractionCode) {
    InfractionInfosDto infractionInfosDto = infractionInfoRepository.findBy(infractionCode);

    return infractionFactory.create(infractionInfosDto);
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
