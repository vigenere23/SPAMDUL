package ca.ulaval.glo4003.spamdul.charging.usecases;

import ca.ulaval.glo4003.spamdul.charging.entities.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.charging.entities.rechargul.RechargULCardFactory;
import ca.ulaval.glo4003.spamdul.charging.entities.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.charging.entities.rechargul.exceptions.RechargULCardNotFoundException;
import ca.ulaval.glo4003.spamdul.charging.usecases.assembler.RechargULDtoAssembler;
import ca.ulaval.glo4003.spamdul.charging.usecases.dto.RechargULCardDto;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.User;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserId;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserRepository;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

public class RechargULUseCase {

  private final UserRepository userRepository;
  private final RechargULCardFactory rechargULCardFactory;
  private final RechargULDtoAssembler rechargULDtoAssembler;

  public RechargULUseCase(UserRepository userRepository,
                          RechargULCardFactory rechargULCardFactory,
                          RechargULDtoAssembler rechargULDtoAssembler) {
    this.userRepository = userRepository;
    this.rechargULCardFactory = rechargULCardFactory;
    this.rechargULDtoAssembler = rechargULDtoAssembler;
  }

  public RechargULCardDto getRechargULCard(RechargULCardId rechargULCardId) {
    try {
      return rechargULDtoAssembler.toDto(userRepository.findBy(rechargULCardId).getRechargULCard());
    } catch (UserNotFoundException e) {
      throw new RechargULCardNotFoundException();
    }
  }

  public RechargULCardDto addCredits(RechargULCardId rechargULCardId, Amount amount) {
    User user = userRepository.findBy(rechargULCardId);

    RechargULCard rechargULCard = user.addRechargULCredits(amount);
    userRepository.save(user);

    return rechargULDtoAssembler.toDto(rechargULCard);
  }

  public RechargULCardDto createCard(UserId userId) {
    RechargULCard card = rechargULCardFactory.create();
    User user = userRepository.findBy(userId);
    user.associate(card);
    userRepository.save(user);

    return rechargULDtoAssembler.toDto(card);
  }
}
