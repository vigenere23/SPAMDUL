package ca.ulaval.glo4003.spamdul.account.api;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;
import ca.ulaval.glo4003.spamdul.account.usecases.AccountUseCase;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("account")
public class AccountResource {

  private final AccountUseCase accountUseCase;
  private final AccountUriBuilder accountUriBuilder;

  public AccountResource(AccountUseCase accountUseCase,
                         AccountUriBuilder accountUriBuilder) {
    this.accountUseCase = accountUseCase;
    this.accountUriBuilder = accountUriBuilder;
  }

  @POST
  public Response createAccount() {
    AccountId accountId = accountUseCase.createAccount();
    return Response.created(accountUriBuilder.build(accountId)).build();
  }
}
