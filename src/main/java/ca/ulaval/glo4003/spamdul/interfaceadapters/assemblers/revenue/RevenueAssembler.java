package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.revenue;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.CarTypeTotalRevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.RevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.TotalRevenueResponse;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.util.Map;

public class RevenueAssembler {

  public RevenueResponse toResponse(Amount amount) {
    RevenueResponse response = new RevenueResponse();
    response.revenue = amount.asDouble();

    return response;
  }

  public CarTypeTotalRevenueResponse toResponse(Map<CarType, Amount> revenueByCarType) {
    // TODO Si on le fait comme ca on s'assure qu'on envoie 0 dans le cas ou aucun achat pour un carType est donnee
    // TODO sinon on itere dessus et on ajoute seulement qui sont present...
    try {
      CarTypeTotalRevenueResponse response = new CarTypeTotalRevenueResponse();
      response.gourmande = revenueByCarType.get(CarType.GOURMANDE).asDouble();
      response.economique = revenueByCarType.get(CarType.ECONOMIQUE).asDouble();
      response.superEconomique = revenueByCarType.get(CarType.SUPER_ECONOMIQUE).asDouble();
      response.hybridEconomique = revenueByCarType.get(CarType.HYBRIDE_ECONOMIQUE).asDouble();
      response.sansPollution = revenueByCarType.get(CarType.SANS_POLLUTION).asDouble();
      response.total = response.gourmande + response.economique + response.superEconomique + response.hybridEconomique
          + response.sansPollution;

      return response;
    } catch (NullPointerException e) {
      throw new MissingACarTypeForResponseException("Missing a car type for response");
    }
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
