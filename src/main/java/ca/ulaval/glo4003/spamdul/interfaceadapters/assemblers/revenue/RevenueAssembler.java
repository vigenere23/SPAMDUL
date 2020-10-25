package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.revenue;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.CarTypeTotalRevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.RevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.TotalRevenueResponse;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class RevenueAssembler {

  public RevenueResponse toResponse(Amount amount) {
    RevenueResponse response = new RevenueResponse();
    response.revenue = amount.asDouble();

    return response;
  }

  public CarTypeTotalRevenueResponse toResponse(Map<CarType, Amount> revenueByCarType) {
    CarTypeTotalRevenueResponse response = new CarTypeTotalRevenueResponse();
    response.byCarType = revenueByCarType.entrySet()
                                         .stream()
                                         .collect(Collectors.toMap(Entry::getKey,
                                                                   e -> e.getValue().asDouble()));

    response.total = revenueByCarType.values()
                                     .stream()
                                     .reduce(Amount.valueOf(0), Amount::add)
                                     .asDouble();

    return response;
  }

  public TotalRevenueResponse toResponse(Amount passTotalRevenue,
                                         Amount infractionsTotalRevenue,
                                         Map<CarType, Amount> revenueByCarType) {
    TotalRevenueResponse totalRevenueResponse = new TotalRevenueResponse();
    totalRevenueResponse.campusAccess = toResponse(revenueByCarType);
    totalRevenueResponse.infraction = infractionsTotalRevenue.asDouble();
    totalRevenueResponse.pass = passTotalRevenue.asDouble();
    totalRevenueResponse.total =
        totalRevenueResponse.campusAccess.total + infractionsTotalRevenue.asDouble()
            + passTotalRevenue.asDouble();

    return totalRevenueResponse;
  }
}
