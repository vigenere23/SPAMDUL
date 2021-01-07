package ca.ulaval.glo4003.spamdul.billing.entities.exceptions;

import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceId;

public class InvoiceNotFoundException extends RuntimeException {

  public InvoiceNotFoundException(InvoiceId invoiceId) {
    super(String.format("Invoice with id %s was not found", invoiceId));
  }
}
