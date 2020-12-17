package ca.ulaval.glo4003.spamdul.context.pass;

import ca.ulaval.glo4003.spamdul.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.entity.delivery.post.DeliveryFeeCalculator;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPassCodeFactory;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPassDeliveryOptionsFactory;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPassFactory;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZoneFeeRepository;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.calendar.HardCodedCalendar;
import ca.ulaval.glo4003.spamdul.infrastructure.db.parking.parkingzonefee.ParkingZoneFeeCsvRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.ids.IncrementalIdGenerator;
import ca.ulaval.glo4003.spamdul.infrastructure.reader.CsvReader;
import ca.ulaval.glo4003.spamdul.assemblers.delivery.DeliveryAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.delivery.EmailAddressAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.delivery.PostalAddressAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.parking.pass.PassAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.timeperiod.TimePeriodAssembler;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import ca.ulaval.glo4003.spamdul.api.pass.ParkingPassResource;

public abstract class PassContext implements ResourceContext {

  protected final DeliveryFeeCalculator deliveryFeeCalculator;
  protected final ParkingZoneFeeRepository parkingZoneFeeRepository;
  protected final ParkingPassDeliveryOptionsFactory parkingPassDeliveryOptionsFactory;
  protected final ParkingPassFactory parkingPassFactory;
  protected final PassAssembler passAssembler;
  protected ParkingPassResource parkingPassResource;

  protected PassContext() {
    Calendar calendar = new HardCodedCalendar();
    TimePeriodFactory timePeriodFactory = new TimePeriodFactory(calendar);
    ParkingPassCodeFactory parkingPassCodeFactory = new ParkingPassCodeFactory(new IncrementalIdGenerator());

    CsvReader csvReader = new CsvReader();

    EmailAddressAssembler emailAddressAssembler = new EmailAddressAssembler();
    PostalAddressAssembler postalAddressAssembler = new PostalAddressAssembler();
    DeliveryAssembler deliveryAssembler = new DeliveryAssembler(emailAddressAssembler, postalAddressAssembler);
    TimePeriodAssembler timePeriodAssembler = new TimePeriodAssembler();

    parkingPassDeliveryOptionsFactory = new ParkingPassDeliveryOptionsFactory();
    parkingPassFactory = new ParkingPassFactory(parkingPassCodeFactory, timePeriodFactory);
    parkingZoneFeeRepository = new ParkingZoneFeeCsvRepository(csvReader,
                                                               "src/main/resources/frais-zone.csv");
    deliveryFeeCalculator = new DeliveryFeeCalculator();
    passAssembler = new PassAssembler(deliveryAssembler, timePeriodAssembler);
  }

  @Override public void registerResources(InstanceMap resources) {
    resources.add(parkingPassResource);
  }
}
