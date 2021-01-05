package ca.ulaval.glo4003.spamdul.parking2.usecases;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodCreationInfos;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRight;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRightFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.CarFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.Permit;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitCreationInfos;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUser;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserId;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserRepository;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.AccessRightCreationDto;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.PermitCreationDto;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.UserCreationDto;

public class ParkingUseCase {

  private final ParkingUserRepository parkingUserRepository;
  private final ParkingUserFactory parkingUserFactory;
  private final CarFactory carFactory;
  private final PermitFactory permitFactory;
  private final AccessRightFactory accessRightFactory;

  public ParkingUseCase(ParkingUserRepository parkingUserRepository,
                        ParkingUserFactory parkingUserFactory,
                        CarFactory carFactory,
                        PermitFactory permitFactory,
                        AccessRightFactory accessRightFactory) {
    this.parkingUserRepository = parkingUserRepository;
    this.parkingUserFactory = parkingUserFactory;
    this.carFactory = carFactory;
    this.permitFactory = permitFactory;
    this.accessRightFactory = accessRightFactory;
  }

  public void createUser(UserCreationDto dto) {
    ParkingUser user = parkingUserFactory.create(dto.name, dto.sex, dto.birthDate);
    parkingUserRepository.save(user);
  }

  public void createPermit(ParkingUserId parkingUserId, PermitCreationDto dto) {
    ParkingUser parkingUser = parkingUserRepository.findBy(parkingUserId);
    PermitCreationInfos infos = new PermitCreationInfos(dto.carBrand,
                                                        dto.carModel,
                                                        dto.carYear,
                                                        dto.licensePlate,
                                                        dto.carType);
    Permit permit = permitFactory.create(dto.type, infos);
    parkingUser.addPermit(permit);
    parkingUserRepository.save(parkingUser);
  }

  public void createAccessRight(ParkingUserId parkingUserId, LicensePlate licensePlate, AccessRightCreationDto dto) {
    ParkingUser parkingUser = parkingUserRepository.findBy(parkingUserId);
    AccessPeriodCreationInfos infos = new AccessPeriodCreationInfos(dto.period.dayOfWeek,
                                                                    dto.period.year,
                                                                    dto.period.month,
                                                                    dto.period.semester,
                                                                    dto.period.start,
                                                                    dto.period.numberOfHours);
    AccessRight accessRight = accessRightFactory.create(dto.period.type, dto.parkingZone, infos);
    parkingUser.addAccessRight(licensePlate, accessRight);
    parkingUserRepository.save(parkingUser);
  }
}
