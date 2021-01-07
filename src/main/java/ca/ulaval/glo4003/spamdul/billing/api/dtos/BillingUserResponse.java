package ca.ulaval.glo4003.spamdul.billing.api.dtos;

import java.util.Set;

public class BillingUserResponse {

  public String accountId;
  public Set<InvoiceResponse> invoices;
}
