package ca.ulaval.glo4003.spamdul.parking.context.pass;

import ca.ulaval.glo4003.spamdul.assemblers.delivery.DeliveryAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.delivery.EmailAddressAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.delivery.PostalAddressAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.parking.pass.PassAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.timeperiod.TimePeriodAssembler;
import ca.ulaval.glo4003.spamdul.parking.api.pass.ParkingPassResource;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.post.DeliveryFeeCalculator;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingPassCodeFactory;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingPassDeliveryOptionsFactory;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingPassFactory;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZoneFeeRepository;
import ca.ulaval.glo4003.spamdul.parking.infrastructure.persistence.parkingzonefee.ParkingZoneFeeCsvRepository;
import ca.ulaval.glo4003.spamdul.shared.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.ids.IncrementalIdGenerator;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.reader.CsvReader;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.TimePeriodFactory;
import ca.ulaval.glo4003.spamdul.time.infrastructure.calendar.HardCodedCalendar;

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
