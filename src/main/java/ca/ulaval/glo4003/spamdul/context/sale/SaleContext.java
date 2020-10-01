package ca.ulaval.glo4003.spamdul.context.sale;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryStrategyFactory;
import ca.ulaval.glo4003.spamdul.entity.pass.PassFactory;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.sale.PassDeliveryOptionsFactory;
import ca.ulaval.glo4003.spamdul.entity.sale.PassSender;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.pass.InMemoryPassRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.user.UserRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.SaleResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.SaleResourceImpl;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.DeliveryAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.EmailAddressAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.PostalAddressAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.PassSaleAssembler;
import ca.ulaval.glo4003.spamdul.usecases.pass.PassService;
import ca.ulaval.glo4003.spamdul.usecases.sale.SaleService;

public class SaleContext {

  private final SaleResource saleResource;
  private final PassRepository passRepository;

  public SaleContext() {
    passRepository = new InMemoryPassRepository();
    UserRepository userRepository = new UserRepositoryInMemory();
    PassFactory passFactory = new PassFactory();
    PassService passService = new PassService(passRepository, passFactory);
    DeliveryStrategyFactory deliveryStrategyFactory = new DeliveryStrategyFactory();
    PassDeliveryOptionsFactory passDeliveryOptionsFactory = new PassDeliveryOptionsFactory();
    PassSender passSender = new PassSender(userRepository, passDeliveryOptionsFactory, deliveryStrategyFactory);
    SaleService saleService = new SaleService(passService, passSender);
    EmailAddressAssembler emailAddressAssembler = new EmailAddressAssembler();
    PostalAddressAssembler postalAddressAssembler = new PostalAddressAssembler();
    DeliveryAssembler deliveryAssembler = new DeliveryAssembler(emailAddressAssembler, postalAddressAssembler);
    PassSaleAssembler passSaleAssembler = new PassSaleAssembler(deliveryAssembler);
    saleResource = new SaleResourceImpl(saleService, passSaleAssembler);
  }

  public SaleResource getSaleResource() {
    return saleResource;
  }

  public PassRepository getPassRepository() {
    return passRepository;
  }
}
