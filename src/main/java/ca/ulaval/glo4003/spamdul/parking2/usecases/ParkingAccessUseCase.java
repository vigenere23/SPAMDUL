package ca.ulaval.glo4003.spamdul.parking2.usecases;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceCreator;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceId;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceItem;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.paid_event.InvoicePaidObservable;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.CarNotFoundException;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidAccessException;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.PermitNotFoundException;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.Infraction;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionCreator;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.association.InfractionAssociationCallbackFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUser;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserRepository;
import ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers.InfractionAssembler;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.InfractionDto;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.ParkingAccessDto;
import ca.ulaval.glo4003.spamdul.shared.usecases.exceptions.ItemNotFoundException;
import ca.ulaval.glo4003.spamdul.shared.usecases.exceptions.UnauthorizedException;
import ca.ulaval.glo4003.spamdul.shared.utils.ListUtil;
import java.util.List;
import java.util.Optional;

public class ParkingAccessUseCase {

  private final ParkingUserRepository parkingUserRepository;
  private final InfractionCreator infractionCreator;
  private final InvoiceCreator invoiceCreator;
  private final InvoicePaidObservable invoicePaidObservable;
  private final InfractionAssociationCallbackFactory infractionAssociationCallbackFactory;
  private final InfractionAssembler infractionAssembler;

  public ParkingAccessUseCase(ParkingUserRepository parkingUserRepository,
                              InfractionCreator infractionCreator,
                              InvoiceCreator invoiceCreator,
                              InvoicePaidObservable invoicePaidObservable,
                              InfractionAssociationCallbackFactory infractionAssociationCallbackFactory,
                              InfractionAssembler infractionAssembler) {
    this.parkingUserRepository = parkingUserRepository;
    this.infractionCreator = infractionCreator;
    this.invoiceCreator = invoiceCreator;
    this.invoicePaidObservable = invoicePaidObservable;
    this.infractionAssociationCallbackFactory = infractionAssociationCallbackFactory;
    this.infractionAssembler = infractionAssembler;
  }

  public void accessParking(ParkingAccessDto dto) {
    try {
      ParkingUser parkingUser = findParkingUser(dto);
      validateAccess(parkingUser, dto);
    } catch (PermitNotFoundException | CarNotFoundException exception) {
      throw new ItemNotFoundException(exception);
    } catch (InvalidAccessException exception) {
      throw new UnauthorizedException(exception);
    }
  }

  public Optional<InfractionDto> giveInfraction(ParkingAccessDto dto) {
    ParkingUser parkingUser = null;

    try {
      parkingUser = findParkingUser(dto);
      validateAccess(parkingUser, dto);
      return Optional.empty();
    } catch (InvalidAccessException exception) {
      Infraction infraction = infractionCreator.createInfraction(exception);
      if (parkingUser != null) {
        InvoiceId invoiceId = createInvoice(parkingUser.getAccountId(), infraction);
        infraction.setInvoiceId(invoiceId);
      }
      InfractionDto infractionDto = infractionAssembler.toDto(infraction);
      return Optional.of(infractionDto);
    }
  }

  private ParkingUser findParkingUser(ParkingAccessDto dto) {
    validateRequiredInfos(dto);

    if (dto.permitNumber != null) {
      return parkingUserRepository.findBy(dto.permitNumber);
    } else {
      return parkingUserRepository.findBy(dto.licensePlate);
    }
  }

  private void validateAccess(ParkingUser parkingUser, ParkingAccessDto dto) {
    validateRequiredInfos(dto);

    if (dto.licensePlate == null) {
      parkingUser.validateAccess(dto.permitNumber, dto.accessDateTime, dto.parkingZone);
    } else if (dto.permitNumber == null) {
      parkingUser.validateAccess(dto.licensePlate, dto.accessDateTime, dto.parkingZone);
    } else {
      parkingUser.validateAccess(dto.permitNumber, dto.licensePlate, dto.accessDateTime, dto.parkingZone);
    }
  }

  private void validateRequiredInfos(ParkingAccessDto dto) {
    if (dto.permitNumber == null && dto.licensePlate == null) {
      throw new IllegalArgumentException("Either a permit number or a license plate is required.");
    }
  }

  private InvoiceId createInvoice(AccountId accountId, Infraction infraction) {
    List<InvoiceItem> invoiceItems = ListUtil.toList(new InvoiceItem(infraction.getAmount(),
                                                                     infraction.toString(),
                                                                     1));
    InvoiceId invoiceId = invoiceCreator.createInvoice(accountId, invoiceItems);
    invoicePaidObservable.register(invoiceId,
                                   infractionAssociationCallbackFactory.create(accountId, infraction));

    return invoiceId;
  }
}
