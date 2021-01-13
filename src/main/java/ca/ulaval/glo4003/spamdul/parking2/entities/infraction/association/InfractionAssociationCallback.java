package ca.ulaval.glo4003.spamdul.parking2.entities.infraction.association;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.paid_event.InvoicePaidInfos;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.paid_event.InvoicePaidObserver;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.Infraction;

public class InfractionAssociationCallback implements InvoicePaidObserver {

  private final InfractionAssociator infractionAssociator;
  private final AccountId accountId;
  private final Infraction infraction;

  public InfractionAssociationCallback(InfractionAssociator infractionAssociator,
                                       AccountId accountId,
                                       Infraction infraction) {
    this.infractionAssociator = infractionAssociator;
    this.accountId = accountId;
    this.infraction = infraction;
  }

  @Override public void handleInvoicePaid(InvoicePaidInfos invoicePaidInfos) {
    infractionAssociator.associateInfraction(accountId, infraction);
  }
}
