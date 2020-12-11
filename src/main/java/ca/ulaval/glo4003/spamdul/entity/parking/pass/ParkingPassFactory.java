package ca.ulaval.glo4003.spamdul.entity.parking.pass;

import static ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType.MONTHLY;
import static ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType.ONE_SEMESTER;
import static ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER;
import static ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType.THREE_SEMESTERS;
import static ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType.TWO_SEMESTERS;
import static jersey.repackaged.com.google.common.collect.Lists.newArrayList;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.bike.BikeParkingPass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.exceptions.InvalidPassPeriodTypeException;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodFactory;
import java.util.ArrayList;

public class ParkingPassFactory {

  private final static ArrayList<PeriodType> ACCEPTED_PERIOD_TYPES = newArrayList(
      SINGLE_DAY_PER_WEEK_PER_SEMESTER,
      MONTHLY,
      ONE_SEMESTER,
      TWO_SEMESTERS,
      THREE_SEMESTERS);

  private final ParkingPassCodeFactory parkingPassCodeFactory;
  private final TimePeriodFactory timePeriodFactory;

  public ParkingPassFactory(ParkingPassCodeFactory parkingPassCodeFactory,
                            TimePeriodFactory timePeriodFactory) {
    this.parkingPassCodeFactory = parkingPassCodeFactory;
    this.timePeriodFactory = timePeriodFactory;
  }

  public ParkingPass create(ParkingZone parkingZone, TimePeriodDto timePeriodDto) {
    if (!ACCEPTED_PERIOD_TYPES.contains(timePeriodDto.periodType)) {
      throw new InvalidPassPeriodTypeException();
    }

    TimePeriod timePeriod = timePeriodFactory.createTimePeriod(timePeriodDto);
    ParkingPassCode parkingPassCode = parkingPassCodeFactory.create(parkingZone);

    if (parkingZone == ParkingZone.ZONE_BIKE) {
      return new BikeParkingPass(parkingPassCode, timePeriod);
    }

    return new CarParkingPass(parkingPassCode, parkingZone, timePeriod);
  }
}
