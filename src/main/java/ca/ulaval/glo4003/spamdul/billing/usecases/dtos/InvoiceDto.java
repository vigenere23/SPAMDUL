package ca.ulaval.glo4003.spamdul.billing.usecases.dtos;

import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceId;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.time.LocalDateTime;
import java.util.Set;

public class InvoiceDto {

  public InvoiceId id;
  public LocalDateTime createdAt;
  public LocalDateTime paidAt;
  public Amount total;
  public String status;
  public Set<InvoiceItemDto> items;
}
