package ca.ulaval.glo4003.spamdul.parking2.usecases;

import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceCreator;
import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceItem;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodCreationInfos;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRight;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRightFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.delivery.DeliveryInfos;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.Permit;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitCreationInfos;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUser;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserId;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserRepository;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.AccessPeriodCreationInfosAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.DeliveryInfosAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.PermitCreationInfosAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.AccessRightCreationDto;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.PermitCreationDto;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.UserCreationDto;

public class ParkingUseCase {

  private final ParkingUserRepository parkingUserRepository;
  private final ParkingUserFactory parkingUserFactory;
  private final PermitFactory permitFactory;
  private final AccessRightFactory accessRightFactory;
  private final DeliveryInfosAssembler deliveryInfosAssembler;
  private final PermitCreationInfosAssembler permitCreationInfosAssembler;
  private final AccessPeriodCreationInfosAssembler accessPeriodCreationInfosAssembler;
  private final InvoiceCreator invoiceCreator;

  public ParkingUseCase(ParkingUserRepository parkingUserRepository,
                        ParkingUserFactory parkingUserFactory,
                        PermitFactory permitFactory,
                        AccessRightFactory accessRightFactory,
                        DeliveryInfosAssembler deliveryInfosAssembler,
                        PermitCreationInfosAssembler permitCreationInfosAssembler,
                        AccessPeriodCreationInfosAssembler accessPeriodCreationInfosAssembler,
                        InvoiceCreator invoiceCreator) {
    this.parkingUserRepository = parkingUserRepository;
    this.parkingUserFactory = parkingUserFactory;
    this.permitFactory = permitFactory;
    this.accessRightFactory = accessRightFactory;
    this.deliveryInfosAssembler = deliveryInfosAssembler;
    this.permitCreationInfosAssembler = permitCreationInfosAssembler;
    this.accessPeriodCreationInfosAssembler = accessPeriodCreationInfosAssembler;
    this.invoiceCreator = invoiceCreator;
  }

  public void createUser(UserCreationDto dto) {
    ParkingUser user = parkingUserFactory.create(dto.name, dto.sex, dto.birthDate);
    parkingUserRepository.save(user);
  }

  public void addPermitToUser(ParkingUserId parkingUserId, PermitCreationDto dto) {
    ParkingUser parkingUser = parkingUserRepository.findBy(parkingUserId);
    DeliveryInfos deliveryInfos = deliveryInfosAssembler.fromDto(dto.delivery); // TODO do something with that
    Permit permit = createPermit(dto);

    InvoiceItem permitInvoiceItem = new InvoiceItem(permit, priceable -> {
      parkingUser.addPermit((Permit) priceable);
      parkingUserRepository.save(parkingUser);
    });
    // TODO add billing item
    invoiceCreator.createInvoice(permitInvoiceItem);

    // parkingUser.addPermit(permit);
    // parkingUserRepository.save(parkingUser);
  }

  public void addAccessRightToUser(ParkingUserId parkingUserId, LicensePlate licensePlate, AccessRightCreationDto dto) {
    ParkingUser parkingUser = parkingUserRepository.findBy(parkingUserId);
    AccessRight accessRight = createAccessRight(dto);

    parkingUser.addAccessRight(licensePlate, accessRight);

    parkingUserRepository.save(parkingUser);
  }

  private Permit createPermit(PermitCreationDto dto) {
    PermitCreationInfos infos = permitCreationInfosAssembler.fromDto(dto);
    return permitFactory.create(dto.type, infos);
  }

  private AccessRight createAccessRight(AccessRightCreationDto dto) {
    AccessPeriodCreationInfos infos = accessPeriodCreationInfosAssembler.fromDto(dto.period);
    return accessRightFactory.create(dto.period.type, dto.parkingZone, infos);
  }
}
