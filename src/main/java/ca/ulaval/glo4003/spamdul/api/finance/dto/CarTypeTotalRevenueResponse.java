package ca.ulaval.glo4003.spamdul.api.finance.dto;

import ca.ulaval.glo4003.spamdul.entity.user.car.CarType;
import java.util.Map;

public class CarTypeTotalRevenueResponse {

  public Map<CarType, Double> byCarType;
  public double total;
}
