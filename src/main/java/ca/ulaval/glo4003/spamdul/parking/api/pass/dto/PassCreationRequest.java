package ca.ulaval.glo4003.spamdul.parking.api.pass.dto;

import ca.ulaval.glo4003.spamdul.time.api.timeperiod.TimePeriodRequest;

public class PassCreationRequest {

  public String parkingZone;
  public DeliveryRequest delivery;
  public String userId;
  public TimePeriodRequest period;
}
