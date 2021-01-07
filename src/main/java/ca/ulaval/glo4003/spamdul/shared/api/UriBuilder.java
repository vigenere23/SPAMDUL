package ca.ulaval.glo4003.spamdul.shared.api;

import ca.ulaval.glo4003.spamdul.shared.utils.UriUtil;

public abstract class UriBuilder {

  protected final String baseUrl;

  protected UriBuilder(String apiUrl, String serviceUrl) {
    this.baseUrl = UriUtil.concatenate(apiUrl, serviceUrl).toString();
  }
}
