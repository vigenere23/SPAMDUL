package ca.ulaval.glo4003.spamdul.account.api;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;
import ca.ulaval.glo4003.spamdul.shared.api.UriBuilder;
import ca.ulaval.glo4003.spamdul.shared.utils.UriUtil;
import java.net.URI;

public class AccountUriBuilder extends UriBuilder {

  private static final String URL = "account";

  public AccountUriBuilder(String apiUrl) {
    super(apiUrl, URL);
  }

  public URI build(AccountId accountId) {
    return UriUtil.concatenate(baseUrl, accountId.toString());
  }
}
