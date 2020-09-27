package ca.ulaval.glo4003.spamdul.usecases.sale;

import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.usecases.pass.PassService;

public class SaleService {

  private PassService passService;

  public SaleService(PassService passService) {
    this.passService = passService;
  }

  public void createSale(SaleDto saleDto) {
    PassCode passCode = passService.createPass(saleDto.passDTO);

    // TODO: Ajouter la logique pour l'envoi selon delivery mode

    switch (saleDto.deliveryMode) {
      case EMAIL:
        System.out.println("The pass code is being sent by email"); break;
      case POST:
        System.out.println("The pass code is being sent by post"); break;
    }
  }
}
