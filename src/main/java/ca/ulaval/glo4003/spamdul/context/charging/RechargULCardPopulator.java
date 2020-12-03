package ca.ulaval.glo4003.spamdul.context.charging;

import ca.ulaval.glo4003.spamdul.context.Populator;
import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.user.car.Car;
import ca.ulaval.glo4003.spamdul.entity.user.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.user.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.user.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardFactory;
import ca.ulaval.glo4003.spamdul.entity.user.Gender;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import java.time.LocalDate;

public class RechargULCardPopulator implements Populator {

  private final RechargULCardFactory rechargULCardFactory;
  private final UserRepository userRepository;

  public RechargULCardPopulator(RechargULCardFactory rechargULCardFactory,
                                UserRepository userRepository) {
    this.rechargULCardFactory = rechargULCardFactory;
    this.userRepository = userRepository;
  }

  @Override public void populate(int numberOfRecords) {
    for (int cardNumber = 0; cardNumber < numberOfRecords; cardNumber++) {
      RechargULCard card = rechargULCardFactory.create();
      User user = new User(UserId.valueOf(Integer.toString(cardNumber + 100)),
                           "name",
                           Gender.MALE,
                           LocalDate.MAX,
                           new Car(new CarId(),
                                   CarType.ECONOMIQUE,
                                   "brand",
                                   "model",
                                   2020,
                                   new LicensePlate("xxx xxx")));
      user.associate(card);
      userRepository.save(user);
    }
  }
}
