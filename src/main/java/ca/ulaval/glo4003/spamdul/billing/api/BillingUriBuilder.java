package ca.ulaval.glo4003.spamdul.billing.api;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceId;
import ca.ulaval.glo4003.spamdul.shared.api.UriBuilder;
import ca.ulaval.glo4003.spamdul.shared.utils.UriUtil;
import java.net.URI;

public class BillingUriBuilder extends UriBuilder {

  private static final String URL = "billing";

  public BillingUriBuilder(String apiUrl) {
    super(apiUrl, URL);
  }

  public URI buildUser(AccountId accountId) {
    return UriUtil.concatenate(baseUrl, "user", accountId.toString());
  }

  public URI buildInvoice(InvoiceId invoiceId) {
    return UriUtil.concatenate(baseUrl, "invoice", invoiceId.toString());
  }
}
