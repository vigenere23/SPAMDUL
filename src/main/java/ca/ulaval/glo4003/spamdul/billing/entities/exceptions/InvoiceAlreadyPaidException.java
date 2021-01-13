package ca.ulaval.glo4003.spamdul.billing.entities.exceptions;

import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceId;

public class InvoiceAlreadyPaidException extends RuntimeException {

  public InvoiceAlreadyPaidException(InvoiceId invoiceId) {
    super(String.format("Invoice with id %s is already paid.", invoiceId));
  }
}
