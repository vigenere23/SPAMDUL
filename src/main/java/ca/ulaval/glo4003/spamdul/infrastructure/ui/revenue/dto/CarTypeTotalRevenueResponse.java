package ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import java.util.Map;

public class CarTypeTotalRevenueResponse {

  public Map<CarType, Double> byCarType;
  public double total;
}
