package ca.ulaval.glo4003.spamdul.parking2.usecases;

import ca.ulaval.glo4003.spamdul.invoice.entities.Invoice;
import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceCreator;
import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceItem;
import ca.ulaval.glo4003.spamdul.invoice.usecases.assemblers.InvoiceAssembler;
import ca.ulaval.glo4003.spamdul.invoice.usecases.dtos.InvoiceDto;
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
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.ParkingUserAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.PermitCreationInfosAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.AccessRightCreationDto;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.ParkingUserDto;
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
  private final ParkingUserAssembler parkingUserAssembler;
  private final InvoiceAssembler invoiceAssembler;
  private final InvoiceCreator invoiceCreator;

  public ParkingUseCase(ParkingUserRepository parkingUserRepository,
                        ParkingUserFactory parkingUserFactory,
                        PermitFactory permitFactory,
                        AccessRightFactory accessRightFactory,
                        DeliveryInfosAssembler deliveryInfosAssembler,
                        PermitCreationInfosAssembler permitCreationInfosAssembler,
                        AccessPeriodCreationInfosAssembler accessPeriodCreationInfosAssembler,
                        ParkingUserAssembler parkingUserAssembler,
                        InvoiceAssembler invoiceAssembler,
                        InvoiceCreator invoiceCreator) {
    this.parkingUserRepository = parkingUserRepository;
    this.parkingUserFactory = parkingUserFactory;
    this.permitFactory = permitFactory;
    this.accessRightFactory = accessRightFactory;
    this.deliveryInfosAssembler = deliveryInfosAssembler;
    this.permitCreationInfosAssembler = permitCreationInfosAssembler;
    this.accessPeriodCreationInfosAssembler = accessPeriodCreationInfosAssembler;
    this.parkingUserAssembler = parkingUserAssembler;
    this.invoiceAssembler = invoiceAssembler;
    this.invoiceCreator = invoiceCreator;
  }

  public ParkingUserDto getUser(ParkingUserId parkingUserId) {
    ParkingUser parkingUser = parkingUserRepository.findBy(parkingUserId);

    return parkingUserAssembler.toDto(parkingUser);
  }

  public ParkingUserDto createUser(UserCreationDto dto) {
    ParkingUser parkingUser = parkingUserFactory.create(dto.name, dto.sex, dto.birthDate);
    parkingUserRepository.save(parkingUser);

    return parkingUserAssembler.toDto(parkingUser);
  }

  public InvoiceDto orderPermit(ParkingUserId parkingUserId, PermitCreationDto dto) {
    ParkingUser parkingUser = parkingUserRepository.findBy(parkingUserId);
    DeliveryInfos deliveryInfos = deliveryInfosAssembler.fromDto(dto.delivery); // TODO do something with that
    Permit permit = createPermit(dto);

    InvoiceItem permitInvoiceItem = new InvoiceItem(permit, priceable -> {
      parkingUser.addPermit((Permit) priceable);
      parkingUserRepository.save(parkingUser);
    });
    // TODO add billing item for delivery

    Invoice invoice = invoiceCreator.createInvoice(permitInvoiceItem);
    return invoiceAssembler.toDto(invoice);
  }

  public InvoiceDto orderAccessRight(ParkingUserId parkingUserId,
                                     LicensePlate licensePlate,
                                     AccessRightCreationDto dto) {
    ParkingUser parkingUser = parkingUserRepository.findBy(parkingUserId);
    AccessRight accessRight = createAccessRight(dto);

    InvoiceItem accessRightInvoiceItem = new InvoiceItem(accessRight, priceable -> {
      parkingUser.addAccessRight(licensePlate, accessRight);
      parkingUserRepository.save(parkingUser);
    });

    Invoice invoice = invoiceCreator.createInvoice(accessRightInvoiceItem);
    return invoiceAssembler.toDto(invoice);
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
