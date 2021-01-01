package ca.ulaval.glo4003.spamdul.finance.api.revenue.dto;

import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.CarType;
import java.util.Map;

public class CarTypeTotalRevenueResponse {

  public Map<CarType, Double> byCarType;
  public double total;
}
