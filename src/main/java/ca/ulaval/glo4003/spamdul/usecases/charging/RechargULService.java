package ca.ulaval.glo4003.spamdul.usecases.charging;

import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardFactory;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.RechargULCardNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.spamdul.shared.amount.Amount;
import ca.ulaval.glo4003.spamdul.usecases.charging.assembler.RechargULDtoAssembler;
import ca.ulaval.glo4003.spamdul.usecases.charging.dto.RechargULCardDto;

public class RechargULService {

  private final UserRepository userRepository;
  private final RechargULCardFactory rechargULCardFactory;
  private RechargULDtoAssembler rechargULDtoAssembler;

  public RechargULService(UserRepository userRepository,
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
