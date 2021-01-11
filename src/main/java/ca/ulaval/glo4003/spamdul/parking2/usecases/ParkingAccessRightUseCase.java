package ca.ulaval.glo4003.spamdul.parking2.usecases;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceCreator;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceId;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceItem;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.paid_event.InvoicePaidObservable;
import ca.ulaval.glo4003.spamdul.parking2.entities.ParkingCarFeeRepository;
import ca.ulaval.glo4003.spamdul.parking2.entities.ParkingZoneFeeRepository;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.creation.AccessPeriodCreationInfos;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRight;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRightFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.association.AccessRightAssociationCallback;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.association.AccessRightAssociationCallbackFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUser;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserRepository;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.AccessPeriodCreationInfosAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.AccessRightCreationDto;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.util.ArrayList;
import java.util.List;

public class ParkingAccessRightUseCase {

  private final ParkingUserRepository parkingUserRepository;
  private final ParkingZoneFeeRepository zoneFeeRepository;
  private final ParkingCarFeeRepository carFeeRepository;
  private final AccessRightFactory accessRightFactory;
  private final AccessPeriodCreationInfosAssembler accessPeriodCreationInfosAssembler;
  private final InvoiceCreator invoiceCreator;
  private final InvoicePaidObservable invoicePaidObservable;
  private final AccessRightAssociationCallbackFactory accessRightAssociationCallbackFactory;

  public ParkingAccessRightUseCase(ParkingUserRepository parkingUserRepository,
                                   ParkingZoneFeeRepository zoneFeeRepository,
                                   ParkingCarFeeRepository carFeeRepository,
                                   AccessRightFactory accessRightFactory,
                                   AccessPeriodCreationInfosAssembler accessPeriodCreationInfosAssembler,
                                   InvoiceCreator invoiceCreator,
                                   InvoicePaidObservable invoicePaidObservable,
                                   AccessRightAssociationCallbackFactory accessRightAssociationCallbackFactory) {
    this.parkingUserRepository = parkingUserRepository;
    this.zoneFeeRepository = zoneFeeRepository;
    this.carFeeRepository = carFeeRepository;
    this.accessRightFactory = accessRightFactory;
    this.accessPeriodCreationInfosAssembler = accessPeriodCreationInfosAssembler;
    this.invoiceCreator = invoiceCreator;
    this.invoicePaidObservable = invoicePaidObservable;
    this.accessRightAssociationCallbackFactory = accessRightAssociationCallbackFactory;
  }

  public InvoiceId orderAccessRights(LicensePlate licensePlate, List<AccessRightCreationDto> dtos) {
    ParkingUser parkingUser = parkingUserRepository.findBy(licensePlate);
    List<AccessRight> accessRights = createAccessRights(dtos);

    return createInvoice(parkingUser.getAccountId(), licensePlate, parkingUser, accessRights);
  }

  private List<AccessRight> createAccessRights(List<AccessRightCreationDto> dtos) {
    List<AccessRight> accessRights = new ArrayList<>();

    dtos.forEach(dto -> {
      AccessPeriodCreationInfos infos = accessPeriodCreationInfosAssembler.fromDto(dto.period);
      accessRights.add(accessRightFactory.create(dto.period.type, dto.parkingZone, infos));
    });

    return accessRights;
  }

  private InvoiceId createInvoice(AccountId accountId,
                                  LicensePlate licensePlate,
                                  ParkingUser parkingUser,
                                  List<AccessRight> accessRights) {
    List<InvoiceItem> invoiceItems = new ArrayList<>();

    accessRights.forEach(accessRight -> {
      Amount price = parkingUser.getAccessRightPrice(zoneFeeRepository, carFeeRepository, licensePlate, accessRight);
      invoiceItems.add(new InvoiceItem(price, accessRight.toString(), 1));
    });

    InvoiceId invoiceId = invoiceCreator.createInvoice(accountId, invoiceItems);
    enqueueAccessRightAssociationActions(invoiceId, licensePlate, accessRights);

    return invoiceId;
  }

  private void enqueueAccessRightAssociationActions(InvoiceId invoiceId,
                                                    LicensePlate licensePlate,
                                                    List<AccessRight> accessRights) {
    for (AccessRight accessRight : accessRights) {
      AccessRightAssociationCallback callback = accessRightAssociationCallbackFactory.create(licensePlate, accessRight);
      invoicePaidObservable.register(invoiceId, callback);
    }
  }
}
