package ca.ulaval.glo4003.spamdul.parking2.usecases;

import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceCreator;
import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceId;
import ca.ulaval.glo4003.spamdul.parking2.entities.delivery.DeliveryInfos;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.Permit;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitAssociator;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitBuyingCallback;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.creation.PermitCreationInfos;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.creation.PermitFactoryProvider;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserId;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserRepository;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.DeliveryInfosAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.PermitCreationInfosAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.permit.PermitCreationDto;

public class ParkingPermitUseCase {

  private final ParkingUserRepository parkingUserRepository;
  private final PermitFactoryProvider permitFactoryProvider;
  private final DeliveryInfosAssembler deliveryInfosAssembler;
  private final PermitCreationInfosAssembler permitCreationInfosAssembler;
  private final InvoiceCreator invoiceCreator;
  private final PermitAssociator permitAssociator;

  public ParkingPermitUseCase(ParkingUserRepository parkingUserRepository,
                              PermitFactoryProvider permitFactoryProvider,
                              DeliveryInfosAssembler deliveryInfosAssembler,
                              PermitCreationInfosAssembler permitCreationInfosAssembler,
                              InvoiceCreator invoiceCreator,
                              PermitAssociator permitAssociator) {
    this.parkingUserRepository = parkingUserRepository;
    this.permitFactoryProvider = permitFactoryProvider;
    this.deliveryInfosAssembler = deliveryInfosAssembler;
    this.permitCreationInfosAssembler = permitCreationInfosAssembler;
    this.invoiceCreator = invoiceCreator;
    this.permitAssociator = permitAssociator;
  }

  public InvoiceId orderPermit(ParkingUserId parkingUserId, PermitCreationDto dto) {
    parkingUserRepository.findBy(parkingUserId);
    DeliveryInfos deliveryInfos = deliveryInfosAssembler.fromDto(dto.delivery); // TODO do something with that
    Permit permit = createPermit(dto);

    InvoiceId invoiceId = invoiceCreator.createInvoice(permit); // TODO add billing item for delivery
    permitAssociator.addListener(invoiceId, new PermitBuyingCallback(parkingUserId, permit));
    // TODO add listener for shipping

    return invoiceId;
  }

  private Permit createPermit(PermitCreationDto dto) {
    PermitCreationInfos infos = permitCreationInfosAssembler.fromDto(dto);
    return permitFactoryProvider.provide(dto.type).create(infos);
  }
}
