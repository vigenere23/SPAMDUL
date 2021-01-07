package ca.ulaval.glo4003.spamdul.parking2.usecases;

import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceCreator;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceId;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.Priceable;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.creation.AccessPeriodCreationInfos;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRight;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRightFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.association.AccessRightAssociationAction;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.association.AccessRightAssociationQueue;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUser;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserRepository;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.AccessPeriodCreationInfosAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.AccessRightCreationDto;
import java.util.ArrayList;
import java.util.List;

public class ParkingAccessRightUseCase {

  private final ParkingUserRepository parkingUserRepository;
  private final AccessRightFactory accessRightFactory;
  private final AccessPeriodCreationInfosAssembler accessPeriodCreationInfosAssembler;
  private final InvoiceCreator invoiceCreator;
  private final AccessRightAssociationQueue accessRightAssociationQueue;

  public ParkingAccessRightUseCase(ParkingUserRepository parkingUserRepository,
                                   AccessRightFactory accessRightFactory,
                                   AccessPeriodCreationInfosAssembler accessPeriodCreationInfosAssembler,
                                   InvoiceCreator invoiceCreator,
                                   AccessRightAssociationQueue accessRightAssociationQueue) {
    this.parkingUserRepository = parkingUserRepository;
    this.accessRightFactory = accessRightFactory;
    this.accessPeriodCreationInfosAssembler = accessPeriodCreationInfosAssembler;
    this.invoiceCreator = invoiceCreator;
    this.accessRightAssociationQueue = accessRightAssociationQueue;
  }

  public InvoiceId orderAccessRights(LicensePlate licensePlate,
                                     List<AccessRightCreationDto> dtos) {
    ParkingUser parkingUser = parkingUserRepository.findBy(licensePlate);
    List<AccessRight> accessRights = createAccessRights(dtos);

    InvoiceId invoiceId = invoiceCreator.createInvoice(parkingUser.getAccountId(), Priceable.fromList(accessRights));
    accessRights.forEach(accessRight -> accessRightAssociationQueue.add(invoiceId,
                                                                        new AccessRightAssociationAction(licensePlate,
                                                                                                         accessRight)));

    return invoiceId;
  }

  private List<AccessRight> createAccessRights(List<AccessRightCreationDto> dtos) {
    List<AccessRight> accessRights = new ArrayList<>();

    dtos.forEach(dto -> {
      AccessPeriodCreationInfos infos = accessPeriodCreationInfosAssembler.fromDto(dto.period);
      accessRights.add(accessRightFactory.create(dto.period.type, dto.parkingZone, infos));
    });

    return accessRights;
  }
}
