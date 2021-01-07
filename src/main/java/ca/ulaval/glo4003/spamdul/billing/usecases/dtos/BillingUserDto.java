package ca.ulaval.glo4003.spamdul.billing.usecases.dtos;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;
import java.util.Set;

public class BillingUserDto {

  public AccountId accountId;
  public Set<InvoiceDto> invoices;
}
