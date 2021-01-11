package ca.ulaval.glo4003.spamdul.parking2.usecases;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceCreator;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceId;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceItem;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.paid_event.InvoicePaidObservable;
import ca.ulaval.glo4003.spamdul.parking2.entities.delivery.DeliveryInfos;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.Permit;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.association.PermitAssociationCallbackFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.creation.PermitCreationInfos;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.creation.PermitFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserRepository;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.DeliveryInfosAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.PermitCreationInfosAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.PermitCreationDto;
import ca.ulaval.glo4003.spamdul.shared.utils.ListUtil;
import java.util.List;

public class ParkingPermitUseCase {

  private final ParkingUserRepository parkingUserRepository;
  private final PermitFactory permitFactory;
  private final DeliveryInfosAssembler deliveryInfosAssembler;
  private final PermitCreationInfosAssembler permitCreationInfosAssembler;
  private final InvoiceCreator invoiceCreator;
  private final InvoicePaidObservable invoicePaidObservable;
  private final PermitAssociationCallbackFactory permitAssociationCallbackFactory;

  public ParkingPermitUseCase(ParkingUserRepository parkingUserRepository,
                              PermitFactory permitFactory,
                              DeliveryInfosAssembler deliveryInfosAssembler,
                              PermitCreationInfosAssembler permitCreationInfosAssembler,
                              InvoiceCreator invoiceCreator,
                              InvoicePaidObservable invoicePaidObservable,
                              PermitAssociationCallbackFactory permitAssociationCallbackFactory) {
    this.parkingUserRepository = parkingUserRepository;
    this.permitFactory = permitFactory;
    this.deliveryInfosAssembler = deliveryInfosAssembler;
    this.permitCreationInfosAssembler = permitCreationInfosAssembler;
    this.invoiceCreator = invoiceCreator;
    this.invoicePaidObservable = invoicePaidObservable;
    this.permitAssociationCallbackFactory = permitAssociationCallbackFactory;
  }

  public InvoiceId orderPermit(AccountId accountId, PermitCreationDto dto) {
    parkingUserRepository.findBy(accountId);
    Permit permit = createPermit(dto);
    DeliveryInfos deliveryInfos = deliveryInfosAssembler.fromDto(dto.delivery); // TODO do something with that

    InvoiceId invoiceId = createInvoice(accountId, permit);

    return invoiceId;
  }

  private Permit createPermit(PermitCreationDto dto) {
    PermitCreationInfos infos = permitCreationInfosAssembler.fromDto(dto);
    return permitFactory.create(dto.type, infos);
  }

  private InvoiceId createInvoice(AccountId accountId, Permit permit) {
    List<InvoiceItem> invoiceItems = ListUtil.toList(new InvoiceItem(permit.getPrice(), permit.toString(), 1));
    InvoiceId invoiceId = invoiceCreator.createInvoice(accountId, invoiceItems);
    invoicePaidObservable.register(invoiceId, permitAssociationCallbackFactory.create(accountId, permit));
    // TODO add delivery item + callback

    return invoiceId;
  }
}
