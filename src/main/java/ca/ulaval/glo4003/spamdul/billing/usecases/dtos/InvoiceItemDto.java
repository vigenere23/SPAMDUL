package ca.ulaval.glo4003.spamdul.billing.usecases.dtos;

import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

public class InvoiceItemDto {

  public String name;
  public Amount subtotal;
  public int quantity;
}
