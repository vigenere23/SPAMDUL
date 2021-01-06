package ca.ulaval.glo4003.spamdul.invoice.usecases;

import ca.ulaval.glo4003.spamdul.invoice.entities.Invoice;
import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceId;
import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceRepository;
import ca.ulaval.glo4003.spamdul.invoice.usecases.assemblers.InvoiceAssembler;
import ca.ulaval.glo4003.spamdul.invoice.usecases.dtos.InvoiceDto;

public class InvoiceUseCase {

  private final InvoiceRepository invoiceRepository;
  private final InvoiceAssembler invoiceAssembler;

  public InvoiceUseCase(InvoiceRepository invoiceRepository,
                        InvoiceAssembler invoiceAssembler) {
    this.invoiceRepository = invoiceRepository;
    this.invoiceAssembler = invoiceAssembler;
  }

  public InvoiceDto getInvoice(InvoiceId invoiceId) {
    Invoice invoice = invoiceRepository.findBy(invoiceId);

    return invoiceAssembler.toDto(invoice);
  }

  public InvoiceDto payInvoice(InvoiceId invoiceId/*, payment infos (credit card)*/) {
    Invoice invoice = invoiceRepository.findBy(invoiceId);
    invoice.pay();
    invoiceRepository.save(invoice);

    return invoiceAssembler.toDto(invoice);
  }
}
