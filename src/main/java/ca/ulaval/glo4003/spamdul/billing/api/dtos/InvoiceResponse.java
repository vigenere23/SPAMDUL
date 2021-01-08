package ca.ulaval.glo4003.spamdul.billing.api.dtos;

import java.util.Set;

public class InvoiceResponse {

  public String id;
  public String createdAt;
  public String paidAt;
  public double total;
  public String status;
  public Set<InvoiceItemResponse> items;
}
