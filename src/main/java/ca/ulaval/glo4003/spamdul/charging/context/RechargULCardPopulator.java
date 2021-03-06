package ca.ulaval.glo4003.spamdul.charging.context;

import ca.ulaval.glo4003.spamdul.charging.entities.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.charging.entities.rechargul.RechargULCardFactory;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.Gender;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.User;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserId;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserRepository;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.CarFactory;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.CarType;
import ca.ulaval.glo4003.spamdul.shared.context.RecordPopulator;
import java.time.LocalDate;

public class RechargULCardPopulator implements RecordPopulator {

  private final CarFactory carFactory;
  private final RechargULCardFactory rechargULCardFactory;
  private final UserRepository userRepository;

  public RechargULCardPopulator(CarFactory carFactory,
                                RechargULCardFactory rechargULCardFactory,
                                UserRepository userRepository) {
    this.carFactory = carFactory;
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
                           carFactory.create(CarType.ECONOMIQUE,
                                             "brand",
                                             "model",
                                             2020,
                                             "xxx xxx"));
      user.associate(card);
      userRepository.save(user);
    }
  }
}
