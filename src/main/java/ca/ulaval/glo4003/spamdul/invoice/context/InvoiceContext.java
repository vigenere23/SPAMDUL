package ca.ulaval.glo4003.spamdul.invoice.context;

import ca.ulaval.glo4003.spamdul.invoice.InvoicePaidObservable;
import ca.ulaval.glo4003.spamdul.invoice.api.InvoiceResource;
import ca.ulaval.glo4003.spamdul.invoice.api.InvoiceUriBuilder;
import ca.ulaval.glo4003.spamdul.invoice.api.assemblers.InvoiceDtoAssembler;
import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceCreator;
import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceFactory;
import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceIdFactory;
import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceRepository;
import ca.ulaval.glo4003.spamdul.invoice.infrastructure.persistence.InvoiceRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.invoice.usecases.InvoiceUseCase;
import ca.ulaval.glo4003.spamdul.invoice.usecases.assemblers.InvoiceAssembler;
import ca.ulaval.glo4003.spamdul.shared.api.ApiUrl;
import ca.ulaval.glo4003.spamdul.shared.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.ids.IncrementalIdGenerator;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;

public class InvoiceContext implements ResourceContext {

  private final InvoiceResource invoiceResource;
  private final InvoiceUriBuilder invoiceUriBuilder;
  private final InvoiceCreator invoiceCreator;
  private final InvoicePaidObservable invoicePaidObservable;

  public InvoiceContext(ApiUrl apiUrl) {
    InvoiceRepository invoiceRepository = new InvoiceRepositoryInMemory();
    InvoiceAssembler invoiceAssembler = new InvoiceAssembler();
    invoicePaidObservable = new InvoicePaidObservable();
    InvoiceUseCase invoiceUseCase = new InvoiceUseCase(invoiceRepository, invoiceAssembler, invoicePaidObservable);
    invoiceResource = new InvoiceResource(invoiceUseCase, new InvoiceDtoAssembler());

    InvoiceFactory invoiceFactory = new InvoiceFactory(new InvoiceIdFactory(new IncrementalIdGenerator()));
    invoiceCreator = new InvoiceCreator(invoiceFactory, invoiceRepository);
    invoiceUriBuilder = new InvoiceUriBuilder(apiUrl.toString());
  }

  @Override
  public void registerResources(InstanceMap instanceMap) {
    instanceMap.add(invoiceResource);
    // TODO add exception mapper
  }

  public InvoiceCreator getInvoiceCreator() {
    return invoiceCreator;
  }

  public InvoicePaidObservable getInvoicePaidObservable() {
    return invoicePaidObservable;
  }

  public InvoiceUriBuilder getInvoiceUriBuilder() {
    return invoiceUriBuilder;
  }
}
