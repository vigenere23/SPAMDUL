package ca.ulaval.glo4003.spamdul.context.pass;

import ca.ulaval.glo4003.spamdul.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.entity.delivery.post.DeliveryFeeCalculator;
import ca.ulaval.glo4003.spamdul.entity.ids.IncrementalLongIdGenerator;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZoneFeeRepository;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCodeFactory;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassDeliveryOptionsFactory;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassFactory;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.calendar.HardCodedCalendar;
import ca.ulaval.glo4003.spamdul.infrastructure.db.parking.parkingzonefee.ParkingZoneFeeCsvRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.reader.CsvReader;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.pass.PassResource;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.DeliveryAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.DeliveryExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.EmailAddressAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.PostalAddressAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.pass.PassAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.pass.PassExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.TimePeriodAssembler;
import ca.ulaval.glo4003.spamdul.utils.InstanceMap;

public abstract class PassContext implements ResourceContext {

  protected final DeliveryFeeCalculator deliveryFeeCalculator;
  protected final ParkingZoneFeeRepository parkingZoneFeeRepository;
  protected final PassDeliveryOptionsFactory passDeliveryOptionsFactory;
  protected final PassFactory passFactory;
  protected final PassAssembler passAssembler;
  protected PassResource passResource;

  protected PassContext() {
    Calendar calendar = new HardCodedCalendar();
    TimePeriodFactory timePeriodFactory = new TimePeriodFactory(calendar);
    PassCodeFactory passCodeFactory = new PassCodeFactory(new IncrementalLongIdGenerator());

    CsvReader csvReader = new CsvReader();

    EmailAddressAssembler emailAddressAssembler = new EmailAddressAssembler();
    PostalAddressAssembler postalAddressAssembler = new PostalAddressAssembler();
    DeliveryAssembler deliveryAssembler = new DeliveryAssembler(emailAddressAssembler, postalAddressAssembler);
    TimePeriodAssembler timePeriodAssembler = new TimePeriodAssembler();

    passDeliveryOptionsFactory = new PassDeliveryOptionsFactory();
    passFactory = new PassFactory(passCodeFactory, timePeriodFactory);
    parkingZoneFeeRepository = new ParkingZoneFeeCsvRepository(csvReader,
                                                               "src/main/resources/frais-zone.csv");
    deliveryFeeCalculator = new DeliveryFeeCalculator();
    passAssembler = new PassAssembler(deliveryAssembler, timePeriodAssembler);
  }

  @Override public void registerResources(InstanceMap resources) {
    resources.add(passResource);
    resources.add(new PassExceptionAssembler());
    resources.add(new DeliveryExceptionAssembler());
  }
}
