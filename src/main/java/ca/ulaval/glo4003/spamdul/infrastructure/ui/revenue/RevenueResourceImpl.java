package ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.CarTypeTotalRevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.RevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.TotalRevenueResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.revenue.RevenueAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.revenue.TransactionQueryAssembler;
import ca.ulaval.glo4003.spamdul.usecases.transactions.TransactionService;
import ca.ulaval.glo4003.spamdul.usecases.transactions.dto.TransactionQueryDto;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.util.Map;

public class RevenueResourceImpl implements RevenueResource {

  private final TransactionService transactionService;
  private final TransactionQueryAssembler transactionQueryAssembler;
  private final RevenueAssembler revenueAssembler;

  public RevenueResourceImpl(TransactionService transactionService,
                             TransactionQueryAssembler transactionQueryAssembler,
                             RevenueAssembler revenueAssembler) {
    this.transactionService = transactionService;
    this.revenueAssembler = revenueAssembler;
    this.transactionQueryAssembler = transactionQueryAssembler;
  }

  @Override
  public TotalRevenueResponse getTotalRevenue(String startDate, String endDate) {
    TransactionQueryDto transactionQueryDto = transactionQueryAssembler.fromValues(startDate, endDate);
    Amount infractionsTotalRevenue = transactionService.getInfractionsTotalRevenue(transactionQueryDto);
    Amount passTotalRevenue = transactionService.getPassTotalRevenue(transactionQueryDto);
    Map<CarType, Amount> revenueByCarType = transactionService.getTotalCampusAccessRevenueByCarType(transactionQueryDto);

    return revenueAssembler.toResponse(passTotalRevenue, infractionsTotalRevenue, revenueByCarType);
  }

  @Override
  public CarTypeTotalRevenueResponse getCarTypeTotalRevenue(String startDate, String endDate) {
    TransactionQueryDto transactionQueryDto = transactionQueryAssembler.fromValues(startDate, endDate);
    Map<CarType, Amount> revenueByCarType = transactionService.getTotalCampusAccessRevenueByCarType(transactionQueryDto);

    return revenueAssembler.toResponse(revenueByCarType);
  }

  @Override
  public RevenueResponse getInfractionsTotalRevenue(String startDate, String endDate) {
    TransactionQueryDto transactionQueryDto = transactionQueryAssembler.fromValues(startDate, endDate);
    Amount amount = transactionService.getInfractionsTotalRevenue(transactionQueryDto);

    return revenueAssembler.toResponse(amount);
  }

  @Override
  public RevenueResponse getParkingPassTotalRevenue(String startDate, String endDate) {
    TransactionQueryDto transactionQueryDto = transactionQueryAssembler.fromValues(startDate, endDate);
    Amount amount = transactionService.getPassTotalRevenue(transactionQueryDto);

    return revenueAssembler.toResponse(amount);
  }
}
