package ca.ulaval.glo4003.spamdul.parking2.entities.infraction;

import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceId;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

public class Infraction {

  private final InfractionId infractionId;
  private final InfractionType infractionType;
  private final Amount amount;
  private InvoiceId invoiceId;

  public Infraction(InfractionId infractionId,
                    InfractionType infractionType, Amount amount) {
    this.infractionId = infractionId;
    this.infractionType = infractionType;
    this.amount = amount;
  }

  public InfractionId getId() {
    return infractionId;
  }

  public InfractionType getInfractionType() {
    return infractionType;
  }

  public Amount getAmount() {
    return amount;
  }

  public InvoiceId getInvoiceId() {
    return invoiceId;
  }

  public void setInvoiceId(InvoiceId invoiceId) {
    if (this.invoiceId == null) {
      this.invoiceId = invoiceId;
    }
  }

  @Override
  public String toString() {
    return String.format("Infraction %s with id %s", infractionType, infractionId);
  }
}
