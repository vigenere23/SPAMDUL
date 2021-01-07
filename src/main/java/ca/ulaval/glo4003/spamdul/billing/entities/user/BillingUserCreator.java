package ca.ulaval.glo4003.spamdul.billing.entities.user;

import ca.ulaval.glo4003.spamdul.account.entities.AccountCreatedObserver;
import ca.ulaval.glo4003.spamdul.account.entities.AccountId;

public class BillingUserCreator implements AccountCreatedObserver {

  private final BillingUserRepository billingUserRepository;
  private final BillingUserFactory billingUserFactory;

  public BillingUserCreator(BillingUserRepository billingUserRepository,
                            BillingUserFactory billingUserFactory) {
    this.billingUserRepository = billingUserRepository;
    this.billingUserFactory = billingUserFactory;
  }

  @Override
  public void handleAccountCreated(AccountId accountId) {
    BillingUser billingUser = billingUserFactory.create(accountId);
    billingUserRepository.save(billingUser);
  }
}
