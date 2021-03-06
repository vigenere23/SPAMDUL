package ca.ulaval.glo4003.spamdul.billing.entities.invoice;

import ca.ulaval.glo4003.spamdul.shared.entities.ids.Id;

public class InvoiceId extends Id {

  private InvoiceId(String value) {
    super(value);
  }

  public static InvoiceId valueOf(String value) {
    return new InvoiceId(value);
  }
}
