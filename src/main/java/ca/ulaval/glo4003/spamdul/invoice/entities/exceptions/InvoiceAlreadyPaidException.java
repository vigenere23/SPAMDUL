package ca.ulaval.glo4003.spamdul.invoice.entities.exceptions;

import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceId;

public class InvoiceAlreadyPaidException extends RuntimeException {

  public InvoiceAlreadyPaidException(InvoiceId invoiceId) {
    super(String.format("Invoice with id %s is already paid.", invoiceId));
  }
}
