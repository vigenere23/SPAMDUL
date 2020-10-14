package ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto;

import java.util.List;

public class TotalRevenueResponse {

  public List<CarTypeRevenueResponse> carTypesRevenue;
  public RevenueResponse campusAccessRevenue;
  public RevenueResponse passRevenue;
  public RevenueResponse infractionRevenue;
  public RevenueResponse totalRevenue;
}
