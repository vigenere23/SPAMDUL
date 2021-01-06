package ca.ulaval.glo4003.spamdul.parking2.usecases;

import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceCreator;
import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceId;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodCreationInfos;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRight;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRightAssociator;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRightBuyingCallback;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRightFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserId;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserRepository;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.AccessPeriodCreationInfosAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.AccessRightCreationDto;

public class ParkingAccessRightUseCase {

  private final ParkingUserRepository parkingUserRepository;
  private final AccessRightFactory accessRightFactory;
  private final AccessPeriodCreationInfosAssembler accessPeriodCreationInfosAssembler;
  private final InvoiceCreator invoiceCreator;
  private final AccessRightAssociator accessRightAssociator;

  public ParkingAccessRightUseCase(ParkingUserRepository parkingUserRepository,
                                   AccessRightFactory accessRightFactory,
                                   AccessPeriodCreationInfosAssembler accessPeriodCreationInfosAssembler,
                                   InvoiceCreator invoiceCreator,
                                   AccessRightAssociator accessRightAssociator) {
    this.parkingUserRepository = parkingUserRepository;
    this.accessRightFactory = accessRightFactory;
    this.accessPeriodCreationInfosAssembler = accessPeriodCreationInfosAssembler;
    this.invoiceCreator = invoiceCreator;
    this.accessRightAssociator = accessRightAssociator;
  }

  public InvoiceId orderAccessRight(ParkingUserId parkingUserId,
                                    LicensePlate licensePlate,
                                    AccessRightCreationDto dto) {
    parkingUserRepository.findBy(parkingUserId);
    AccessRight accessRight = createAccessRight(dto);

    InvoiceId invoiceId = invoiceCreator.createInvoice(accessRight);
    accessRightAssociator.addListener(invoiceId,
                                      new AccessRightBuyingCallback(parkingUserId, licensePlate, accessRight));

    return invoiceId;
  }

  private AccessRight createAccessRight(AccessRightCreationDto dto) {
    AccessPeriodCreationInfos infos = accessPeriodCreationInfosAssembler.fromDto(dto.period);
    return accessRightFactory.create(dto.period.type, dto.parkingZone, infos);
  }
}
