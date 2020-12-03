package ca.ulaval.glo4003.spamdul.usecases.charging;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardFactory;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardRepository;
import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.RechargULCardNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.spamdul.usecases.charging.dto.RechargUlDto;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public class RechargULService {

  private final UserRepository userRepository;
  private final RechargULCardFactory rechargULCardFactory;

  public RechargULService(UserRepository userRepository,
                          RechargULCardFactory rechargULCardFactory) {
    this.userRepository = userRepository;
    this.rechargULCardFactory = rechargULCardFactory;
  }

  public RechargULCard getRechargULCard(RechargULCardId rechargULCardId) {
    try {
      return userRepository.findBy(rechargULCardId).getRechargUlCard();
    } catch (UserNotFoundException e) {
      throw new RechargULCardNotFoundException();
    }
  }

  public RechargULCard addCredits(RechargULCardId rechargULCardId, Amount amount) {
    User user = userRepository.findBy(rechargULCardId);

    RechargULCard rechargULCard = user.addRechargUlCredits(amount);

    userRepository.save(user);

    return rechargULCard;
  }

  public RechargULCard createCard(RechargUlDto rechargUlDto) {
    RechargULCard card = rechargULCardFactory.create();
    User user = userRepository.findBy(rechargUlDto.userId);
    user.associate(card);
    userRepository.save(user);

    return card;
  }
}
