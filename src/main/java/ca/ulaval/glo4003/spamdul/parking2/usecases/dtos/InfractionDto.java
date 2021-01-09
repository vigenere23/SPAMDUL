package ca.ulaval.glo4003.spamdul.parking2.usecases.dtos;

import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceId;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionType;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

public class InfractionDto {

  public InfractionType infractionType;
  public String code;
  public Amount amount;
  public InvoiceId invoiceId;
}
