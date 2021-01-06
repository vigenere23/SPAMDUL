package ca.ulaval.glo4003.spamdul.invoice.api;

import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceId;
import ca.ulaval.glo4003.spamdul.shared.utils.UriUtil;
import java.net.URI;

public class InvoiceUriBuilder {

  private static final String URL = "invoice";
  private final String baseUrl;

  public InvoiceUriBuilder(String apiUrl) {
    this.baseUrl = UriUtil.concatenate(apiUrl, URL).toString();
  }

  public URI buildGet(InvoiceId invoiceId) {
    return UriUtil.concatenate(baseUrl, invoiceId.toString());
  }
}
