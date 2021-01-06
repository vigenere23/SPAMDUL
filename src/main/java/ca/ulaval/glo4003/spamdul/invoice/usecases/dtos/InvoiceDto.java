package ca.ulaval.glo4003.spamdul.invoice.usecases.dtos;

import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceId;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.time.LocalDateTime;

public class InvoiceDto {

  public InvoiceId id;
  public LocalDateTime createdAt;
  public Amount total;
  public String status;
}
