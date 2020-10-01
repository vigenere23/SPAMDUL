package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.InvalidCampusAccessCodeFormat;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.PassType;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.dto.PassSaleRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.DeliveryAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.*;
import ca.ulaval.glo4003.spamdul.usecases.sale.PassSaleDto;

import java.time.DayOfWeek;

public class PassSaleAssembler {

  private DeliveryAssembler deliveryAssembler;

  public PassSaleAssembler(DeliveryAssembler deliveryAssembler) {
    this.deliveryAssembler = deliveryAssembler;
  }

  public PassSaleDto fromRequest(PassSaleRequest passSaleRequest) {
    PassSaleDto passSaleDto = new PassSaleDto();

    passSaleDto.deliveryDto = deliveryAssembler.fromDto(passSaleRequest.deliveryInfos);
    passSaleDto.parkingZone = getParkingZone(passSaleRequest.parkingZone);
    passSaleDto.campusAccessCode = getCampusAccessCode(passSaleRequest.campusAccessCode);
    passSaleDto.passType = getPassType(passSaleRequest.passType);
    passSaleDto.dayOfWeek = getDayOfWeek(passSaleRequest.dayOfWeek);

    return passSaleDto;
  }

  private DayOfWeek getDayOfWeek(String dayOfWeekString) {
    try {
      return DayOfWeek.valueOf(dayOfWeekString.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new InvalidPassSaleDayOfWeekException("The day for the pass must be a valid day of the week");
    }
  }

  private ParkingZone getParkingZone(String parkingZone) {
    try {
      return ParkingZone.valueOf(parkingZone.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new InvalidParkingZoneExceptionSale("The parking zone is invalid");
    }
  }

  private PassType getPassType(String passType) {
    try {
      return PassType.valueOf(passType.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new InvalidPassSaleTypeException("The pass type is invalid");
    }
  }

  private CampusAccessCode getCampusAccessCode(String userId) {
    try {
      return CampusAccessCode.valueOf(userId.toUpperCase());
    } catch (InvalidCampusAccessCodeFormat e) {
      throw new InvalidCampusAccessCodeExceptionSale("The campus access code is not in the right format");
    }
  }
}
