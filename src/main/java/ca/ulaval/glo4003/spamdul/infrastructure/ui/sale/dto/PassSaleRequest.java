package ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.dto;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.timeperiod.dto.TimePeriodRequest;

public class PassSaleRequest {
    public String parkingZone;
    public DeliveryRequest deliveryInfos;
    public String campusAccessCode;
    public TimePeriodRequest period;
}
