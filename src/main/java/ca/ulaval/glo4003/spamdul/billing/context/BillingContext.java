package ca.ulaval.glo4003.spamdul.billing.context;

import ca.ulaval.glo4003.spamdul.account.entities.AccountCreatedObservable;
import ca.ulaval.glo4003.spamdul.billing.InvoicePaidObservable;
import ca.ulaval.glo4003.spamdul.billing.api.BillingResource;
import ca.ulaval.glo4003.spamdul.billing.api.BillingUriBuilder;
import ca.ulaval.glo4003.spamdul.billing.api.assemblers.BillingUserDtoAssembler;
import ca.ulaval.glo4003.spamdul.billing.api.assemblers.InvoiceDtoAssembler;
import ca.ulaval.glo4003.spamdul.billing.api.assemblers.InvoiceItemDtoAssembler;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceCreator;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceFactory;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceIdFactory;
import ca.ulaval.glo4003.spamdul.billing.entities.user.BillingUserCreator;
import ca.ulaval.glo4003.spamdul.billing.entities.user.BillingUserFactory;
import ca.ulaval.glo4003.spamdul.billing.entities.user.BillingUserRepository;
import ca.ulaval.glo4003.spamdul.billing.infrastructure.persistence.BillingUserRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.billing.usecases.BillingUseCase;
import ca.ulaval.glo4003.spamdul.billing.usecases.assemblers.BillingUserAssembler;
import ca.ulaval.glo4003.spamdul.billing.usecases.assemblers.InvoiceAssembler;
import ca.ulaval.glo4003.spamdul.billing.usecases.assemblers.InvoiceItemAssembler;
import ca.ulaval.glo4003.spamdul.shared.api.ApiUrl;
import ca.ulaval.glo4003.spamdul.shared.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.ids.IncrementalIdGenerator;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;

public class BillingContext implements ResourceContext {

  private final BillingResource billingResource;
  private final BillingUriBuilder billingUriBuilder;
  private final InvoiceCreator invoiceCreator;
  private final InvoicePaidObservable invoicePaidObservable;

  public BillingContext(ApiUrl apiUrl, AccountCreatedObservable accountCreatedObservable) {
    BillingUserRepository billingUserRepository = new BillingUserRepositoryInMemory();
    BillingUserFactory billingUserFactory = new BillingUserFactory();
    BillingUserCreator billingUserCreator = new BillingUserCreator(billingUserRepository, billingUserFactory);

    accountCreatedObservable.register(billingUserCreator);

    InvoiceAssembler invoiceAssembler = new InvoiceAssembler(new InvoiceItemAssembler());
    BillingUserAssembler billingUserAssembler = new BillingUserAssembler(invoiceAssembler);
    InvoiceDtoAssembler invoiceDtoAssembler = new InvoiceDtoAssembler(new InvoiceItemDtoAssembler());
    invoicePaidObservable = new InvoicePaidObservable();
    BillingUseCase billingUseCase = new BillingUseCase(billingUserRepository,
                                                       billingUserAssembler,
                                                       invoiceAssembler,
                                                       invoicePaidObservable);
    billingResource = new BillingResource(billingUseCase,
                                          new BillingUserDtoAssembler(invoiceDtoAssembler),
                                          invoiceDtoAssembler);

    InvoiceFactory invoiceFactory = new InvoiceFactory(new InvoiceIdFactory(new IncrementalIdGenerator()));
    invoiceCreator = new InvoiceCreator(invoiceFactory, billingUserRepository);
    billingUriBuilder = new BillingUriBuilder(apiUrl.toString());
  }

  @Override
  public void registerResources(InstanceMap instanceMap) {
    instanceMap.add(billingResource);
    // TODO add exception mapper
  }

  public InvoiceCreator getInvoiceCreator() {
    return invoiceCreator;
  }

  public InvoicePaidObservable getInvoicePaidObservable() {
    return invoicePaidObservable;
  }

  public BillingUriBuilder getInvoiceUriBuilder() {
    return billingUriBuilder;
  }
}
