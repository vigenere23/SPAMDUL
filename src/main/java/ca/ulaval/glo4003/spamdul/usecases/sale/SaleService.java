package ca.ulaval.glo4003.spamdul.usecases.sale;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryBridgeFactory;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.sale.PassSender;
import ca.ulaval.glo4003.spamdul.usecases.pass.PassService;


public class SaleService {

  private PassService passService;
  private final PassSender passSender;

  public SaleService(PassService passService,
                     PassSender passSender) {
    this.passService = passService;
    this.passSender = passSender;
  }

  public void createSale(PassSaleDto passSaleDto) {
    PassCode passCode = passService.createPass(passSaleDto.userId, passSaleDto.parkingZone, passSaleDto.passType);

    passSender.sendPass(passSaleDto.userId, passSaleDto.deliveryDto, passCode);
  }
}
