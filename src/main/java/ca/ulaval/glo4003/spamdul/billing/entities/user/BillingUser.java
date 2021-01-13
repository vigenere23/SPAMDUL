package ca.ulaval.glo4003.spamdul.billing.entities.user;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;
import ca.ulaval.glo4003.spamdul.billing.entities.exceptions.InvoiceNotFoundException;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.Invoice;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceId;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceStatus;
import java.util.HashSet;
import java.util.Set;

public class BillingUser {

  private final AccountId accountId;
  private final Set<Invoice> invoices = new HashSet<>();

  public BillingUser(AccountId accountId) {
    this.accountId = accountId;
  }

  public void addInvoice(Invoice invoice) {
    invoices.add(invoice);
  }

  public Invoice payInvoice(InvoiceId invoiceId) {
    Invoice invoice = findInvoice(invoiceId);
    invoice.pay();
    return invoice;
  }

  public boolean hasInvoice(InvoiceId invoiceId) {
    try {
      findInvoice(invoiceId);
      return true;
    } catch (InvoiceNotFoundException exception) {
      return false;
    }
  }

  private boolean hasUnpaidInvoices() {
    return invoices.stream().anyMatch(invoice -> invoice.getStatus().equals(InvoiceStatus.UNPAID));
  }

  private Invoice findInvoice(InvoiceId invoiceId) {
    return invoices.stream()
                   .filter(invoice -> invoice.getId().equals(invoiceId))
                   .findFirst()
                   .orElseThrow(() -> new InvoiceNotFoundException(invoiceId));
  }

  public Set<Invoice> getInvoices() {
    return invoices;
  }

  public AccountId getAccountId() {
    return accountId;
  }
}
