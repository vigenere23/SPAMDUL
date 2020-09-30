package ca.ulaval.glo4003.spamdul.usecases.sale;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassType;
import ca.ulaval.glo4003.spamdul.entity.sale.PassSender;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.usecases.pass.PassService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SaleServiceTest {
  private final UserId A_USER_ID = new UserId();
  private final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_2;
  private final PassType A_PASS_TYPE = PassType.MONTHLY;
  private final PassCode A_PASS_CODE = new PassCode();
  private DeliveryDto deliveryDto = new DeliveryDto();
  private PassSaleDto passSaleDto = new PassSaleDto();
  private PassService passService;
  private SaleService saleService;
  private PassSender passSender;

  @Before
  public void setUp() {
    passSaleDto.userId = A_USER_ID;
    passSaleDto.passType = A_PASS_TYPE;
    passSaleDto.parkingZone = A_PARKING_ZONE;
    passSaleDto.deliveryDto = deliveryDto;

    passService = mock(PassService.class);
    passSender = mock(PassSender.class);

    given(passService.createPass(A_USER_ID, A_PARKING_ZONE, A_PASS_TYPE)).willReturn(A_PASS_CODE);

    saleService = new SaleService(passService, passSender);
  }

  @Test
  public void whenCreatingSale_thenShouldCallPassServiceToCreatePass() {
    saleService.createSale(passSaleDto);

    verify(passService).createPass(A_USER_ID, A_PARKING_ZONE, A_PASS_TYPE);
  }

  @Test
  public void whenCreatingSale_thenShouldCallPassSenderToDelivePass() {
    saleService.createSale(passSaleDto);

    verify(passSender).sendPass(A_USER_ID, deliveryDto, A_PASS_CODE);
  }
}
