package ca.ulaval.glo4003.spamdul.invoice.entities.exceptions;

import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceId;

public class InvoiceNotFoundException extends RuntimeException {

  public InvoiceNotFoundException(InvoiceId invoiceId) {
    super(String.format("Invoice with id %s was not found", invoiceId));
  }
}
