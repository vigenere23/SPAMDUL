package ca.ulaval.glo4003.spamdul.entity.transactions;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import java.time.LocalDateTime;

public class TransactionDto {

  public double amount;
  public CarType carType;
  public TransactionType transactionType;
  public LocalDateTime createdAt;
}
