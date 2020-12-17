package ca.ulaval.glo4003.spamdul.parking.usecases.infraction;

import ca.ulaval.glo4003.spamdul.authentication.entities.TemporaryToken;
import ca.ulaval.glo4003.spamdul.authentication.entities.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.InfractionTransactionService;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.InfractionFactory;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.InfractionInfoRepository;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.InfractionInfosDto;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.exceptions.InfractionNotFoundException;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.validators.CarParkingPassValidator;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.User;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserRepository;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.spamdul.parking.usecases.infraction.dto.InfractionDto;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

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
