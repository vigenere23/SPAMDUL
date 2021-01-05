package ca.ulaval.glo4003.spamdul.invoice.infrastructure.persistence;

import ca.ulaval.glo4003.spamdul.invoice.entities.Invoice;
import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceId;
import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceRepository;
import ca.ulaval.glo4003.spamdul.invoice.entities.exceptions.InvoiceNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InvoiceRepositoryInMemory implements InvoiceRepository {

  private final Map<InvoiceId, Invoice> invoices = new HashMap<>();

  @Override
  public Invoice findBy(InvoiceId invoiceId) {
    return Optional.ofNullable(invoices.get(invoiceId)).orElseThrow(() -> new InvoiceNotFoundException(invoiceId));
  }

  @Override
  public void save(Invoice invoice) {
    invoices.put(invoice.getId(), invoice);
  }
}
