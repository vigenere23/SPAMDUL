package ca.ulaval.glo4003.spamdul.invoice.entities;

import ca.ulaval.glo4003.spamdul.shared.entities.ids.IdGenerator;

public class InvoiceIdFactory {

  private final IdGenerator idGenerator;

  public InvoiceIdFactory(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  public InvoiceId create() {
    return InvoiceId.valueOf(idGenerator.generate());
  }
}
