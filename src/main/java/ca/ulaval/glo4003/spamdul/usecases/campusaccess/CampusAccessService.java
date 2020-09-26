package ca.ulaval.glo4003.spamdul.usecases.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFactory;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessRepository;
import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.car.CarService;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserService;

public class CampusAccessService {

  private UserService userService;
  private CarService carService;
  private CampusAccessFactory campusAccessFactory;
  private CampusAccessRepository campusAccessRepository;

  public CampusAccessService(UserService userService,
                             CarService carService,
                             CampusAccessFactory campusAccessFactory,
                             CampusAccessRepository campusAccessRepository) {
    this.userService = userService;
    this.carService = carService;
    this.campusAccessFactory = campusAccessFactory;
    this.campusAccessRepository = campusAccessRepository;
  }

  public CampusAccess createNewCampusAccess(CampusAccessDto campusAccessDto) {
    UserId userId = userService.createUser(campusAccessDto.userDto);
    CarId carId = carService.createCar(campusAccessDto.carDto);
    CampusAccess campusAccess = campusAccessFactory.create(userId,
                                                           carId,
                                                           campusAccessDto.period,
                                                           campusAccessDto.dayToAccessCampus);
    //TODO saleService.buyingCampusAccess(campusAccessDto.period, campusAccessDto.carDto.type);
    campusAccessRepository.save(campusAccess);

    return campusAccess;
  }
}
