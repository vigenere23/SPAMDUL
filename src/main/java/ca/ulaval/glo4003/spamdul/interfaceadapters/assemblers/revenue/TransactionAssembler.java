package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.revenue;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.CarTypeRevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.CarTypeTotalRevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.RevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.TotalRevenueResponse;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TransactionAssembler {

  public RevenueResponse toResponse(Amount amount) {
    RevenueResponse response = new RevenueResponse();
    response.revenue = amount.getAmount();

    return response;
  }

  public CarTypeTotalRevenueResponse toResponse(Map<CarType, Amount> revenueByCarType) {
    List<CarTypeRevenueResponse> carTypeRevenueResponses = new ArrayList<>();

    for (Entry<CarType, Amount> entry : revenueByCarType.entrySet()) {
      CarTypeRevenueResponse carTypeRevenueResponse = new CarTypeRevenueResponse();
      carTypeRevenueResponse.carType = entry.getKey();
      carTypeRevenueResponse.revenue = entry.getValue().getAmount();
      carTypeRevenueResponses.add(carTypeRevenueResponse);
    }

    CarTypeTotalRevenueResponse response = new CarTypeTotalRevenueResponse();
    response.carTypesRevenue = carTypeRevenueResponses;

    return response;
  }

  public TotalRevenueResponse toResponse(Amount campusAccessTotalRevenue,
                                         Amount passTotalRevenue,
                                         Amount infractionsTotalRevenue,
                                         Map<CarType, Amount> revenueByCarType) {
    TotalRevenueResponse totalRevenueResponse = new TotalRevenueResponse();
    totalRevenueResponse.campusAccessRevenue = toResponse(campusAccessTotalRevenue);
    totalRevenueResponse.carTypesRevenue = toResponse(revenueByCarType).carTypesRevenue;
    totalRevenueResponse.infractionRevenue = toResponse(infractionsTotalRevenue);
    totalRevenueResponse.passRevenue = toResponse(passTotalRevenue);

    return totalRevenueResponse;
  }
}
