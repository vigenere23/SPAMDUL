package ca.ulaval.glo4003.spamdul.finance.api.revenue.dto;

import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.CarType;
import java.util.Objects;

public class CarTypeRevenueResponse {

  public CarType carType;
  public double revenue;

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CarTypeRevenueResponse that = (CarTypeRevenueResponse) o;

    return Double.compare(that.revenue, revenue) == 0 &&
        carType == that.carType;
  }

  @Override public int hashCode() {
    return Objects.hash(carType, revenue);
  }
}
