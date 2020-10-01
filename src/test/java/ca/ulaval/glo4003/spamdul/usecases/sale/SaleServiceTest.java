package ca.ulaval.glo4003.spamdul.usecases.sale;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassType;
import ca.ulaval.glo4003.spamdul.entity.sale.PassSender;
import ca.ulaval.glo4003.spamdul.usecases.pass.PassService;
import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SaleServiceTest {
  private final CampusAccessCode A_CAMPUS_ACCESS_CODE = new CampusAccessCode();
  private final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_2;
  private final PassType A_PASS_TYPE = PassType.MONTHLY;
  private final PassCode A_PASS_CODE = new PassCode();
  private final DayOfWeek A_DAY_OF_WEEK = DayOfWeek.MONDAY;

  private DeliveryDto deliveryDto = new DeliveryDto();
  private PassSaleDto passSaleDto = new PassSaleDto();
  private PassService passService;
  private PassSender passSender;

  private SaleService saleService;

  @Before
  public void setUp() {
    passSaleDto.campusAccessCode = A_CAMPUS_ACCESS_CODE;
    passSaleDto.passType = A_PASS_TYPE;
    passSaleDto.parkingZone = A_PARKING_ZONE;
    passSaleDto.deliveryDto = deliveryDto;
    passSaleDto.dayOfWeek = A_DAY_OF_WEEK;

    passService = mock(PassService.class);
    passSender = mock(PassSender.class);

    given(passService.createPass(A_CAMPUS_ACCESS_CODE, A_PARKING_ZONE, A_PASS_TYPE, A_DAY_OF_WEEK))
            .willReturn(A_PASS_CODE);

    saleService = new SaleService(passService, passSender);
  }

  @Test
  public void whenCreatingSale_thenShouldCallPassServiceToCreatePass() {
    saleService.createSale(passSaleDto);

    verify(passService).createPass(A_CAMPUS_ACCESS_CODE, A_PARKING_ZONE, A_PASS_TYPE, A_DAY_OF_WEEK);
  }

  @Test
  public void whenCreatingSale_thenShouldCallPassSenderToDeliverPass() {
    saleService.createSale(passSaleDto);

    verify(passSender).sendPass(deliveryDto, A_PASS_CODE);
  }
}
