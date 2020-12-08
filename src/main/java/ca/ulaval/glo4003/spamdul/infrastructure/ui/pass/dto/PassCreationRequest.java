package ca.ulaval.glo4003.spamdul.infrastructure.ui.pass.dto;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.timeperiod.dto.TimePeriodRequest;

public class PassCreationRequest {

  public String parkingZone;
  public DeliveryRequest delivery;
  public String userId;
  public TimePeriodRequest period;
}
