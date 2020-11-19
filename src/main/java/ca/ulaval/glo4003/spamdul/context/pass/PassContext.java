package ca.ulaval.glo4003.spamdul.context.pass;

import ca.ulaval.glo4003.spamdul.entity.bank.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryStrategyFactory;
import ca.ulaval.glo4003.spamdul.entity.delivery.email.EmailServiceProvider;
import ca.ulaval.glo4003.spamdul.entity.delivery.post.DeliveryFeeCalculator;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZoneFeeRepository;
import ca.ulaval.glo4003.spamdul.entity.pass.PassDeliveryOptionsFactory;
import ca.ulaval.glo4003.spamdul.entity.pass.PassFactory;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.pass.PassSender;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodFactory;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.calendar.HardCodedCalendar;
import ca.ulaval.glo4003.spamdul.infrastructure.db.parkingzonefee.ParkingZoneFeeCsvRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.reader.CsvReader;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.pass.PassResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.pass.PassResourceImpl;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.DeliveryAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.EmailAddressAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.PostalAddressAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.pass.PassAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.TimePeriodAssembler;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.CampusAccessService;
import ca.ulaval.glo4003.spamdul.usecases.pass.PassService;

public class PassContext {

  private final PassResource passResource;

  public PassContext(BankRepository bankRepository,
                     CampusAccessService campusAccessService) {
    Calendar calendar = new HardCodedCalendar();
    TimePeriodFactory timePeriodFactory = new TimePeriodFactory(calendar);
    PassFactory passFactory = new PassFactory(timePeriodFactory);

    DeliveryStrategyFactory deliveryStrategyFactory = new DeliveryStrategyFactory(new EmailServiceProvider(System.getenv(
        "API_MODE")));
    PassDeliveryOptionsFactory passDeliveryOptionsFactory = new PassDeliveryOptionsFactory();
    TransactionFactory transactionFactory = new TransactionFactory();
    CsvReader csvReader = new CsvReader();
    ParkingZoneFeeRepository parkingZoneFeeRepository = new ParkingZoneFeeCsvRepository(csvReader,
                                                                                        "src/main/resources/frais-zone.csv");
    PassSender passSender = new PassSender(passDeliveryOptionsFactory, deliveryStrategyFactory);
    DeliveryFeeCalculator deliveryFeeCalculator = new DeliveryFeeCalculator();
    PassService passService = new PassService(passFactory,
                                              campusAccessService,
                                              passSender,
                                              transactionFactory,
                                              bankRepository,
                                              parkingZoneFeeRepository,
                                              deliveryFeeCalculator);

    EmailAddressAssembler emailAddressAssembler = new EmailAddressAssembler();
    PostalAddressAssembler postalAddressAssembler = new PostalAddressAssembler();
    DeliveryAssembler deliveryAssembler = new DeliveryAssembler(emailAddressAssembler, postalAddressAssembler);
    TimePeriodAssembler timePeriodAssembler = new TimePeriodAssembler();
    PassAssembler passAssembler = new PassAssembler(deliveryAssembler, timePeriodAssembler);
    passResource = new PassResourceImpl(passService, passAssembler);
  }

  public PassResource getPassResource() {
    return passResource;
  }
}
