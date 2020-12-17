package ca.ulaval.glo4003.spamdul.api.pass.dto;

import ca.ulaval.glo4003.spamdul.api.timeperiod.dto.TimePeriodRequest;

public class PassCreationRequest {

  public String parkingZone;
  public DeliveryRequest delivery;
  public String userId;
  public TimePeriodRequest period;
}
