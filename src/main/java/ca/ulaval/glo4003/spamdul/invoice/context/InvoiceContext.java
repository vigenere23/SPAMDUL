package ca.ulaval.glo4003.spamdul.invoice.context;

import ca.ulaval.glo4003.spamdul.invoice.api.InvoiceResource;
import ca.ulaval.glo4003.spamdul.invoice.api.assemblers.InvoiceDtoAssembler;
import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceCreator;
import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceFactory;
import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceIdFactory;
import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceRepository;
import ca.ulaval.glo4003.spamdul.invoice.infrastructure.persistence.InvoiceRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.invoice.usecases.InvoiceUseCase;
import ca.ulaval.glo4003.spamdul.invoice.usecases.assemblers.InvoiceAssembler;
import ca.ulaval.glo4003.spamdul.shared.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.ids.IncrementalIdGenerator;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;

public class InvoiceContext implements ResourceContext {

  private final InvoiceResource invoiceResource;
  private final InvoiceCreator invoiceCreator;
  private final InvoiceAssembler invoiceAssembler;
  private final InvoiceDtoAssembler invoiceDtoAssembler;

  public InvoiceContext() {
    InvoiceRepository invoiceRepository = new InvoiceRepositoryInMemory();
    invoiceAssembler = new InvoiceAssembler();
    invoiceDtoAssembler = new InvoiceDtoAssembler();
    InvoiceUseCase invoiceUseCase = new InvoiceUseCase(invoiceRepository, invoiceAssembler);
    invoiceResource = new InvoiceResource(invoiceUseCase, new InvoiceDtoAssembler());

    InvoiceFactory invoiceFactory = new InvoiceFactory(new InvoiceIdFactory(new IncrementalIdGenerator()));
    invoiceCreator = new InvoiceCreator(invoiceFactory, invoiceRepository);
  }

  @Override
  public void registerResources(InstanceMap instanceMap) {
    instanceMap.add(invoiceResource);
    // TODO add exception mapper
  }

  public InvoiceCreator getInvoiceCreator() {
    return invoiceCreator;
  }

  public InvoiceAssembler getInvoiceAssembler() {
    return invoiceAssembler;
  }

  public InvoiceDtoAssembler getInvoiceDtoAssembler() {
    return invoiceDtoAssembler;
  }
}
