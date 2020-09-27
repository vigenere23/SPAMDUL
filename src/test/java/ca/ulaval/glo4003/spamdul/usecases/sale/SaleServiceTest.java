package ca.ulaval.glo4003.spamdul.usecases.sale;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.PassType;
import ca.ulaval.glo4003.spamdul.entity.sale.DeliveryMode;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.usecases.pass.PassDto;
import ca.ulaval.glo4003.spamdul.usecases.pass.PassService;
import org.junit.Before;
import org.junit.Test;

public class SaleServiceTest {

  private final UserId A_USER_ID = new UserId();
  private final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_2;
  private final PassType A_PASS_TYPE = PassType.MONTHLY;
  private final DeliveryMode A_DELIVERY_MODE = DeliveryMode.EMAIL;
  private  PassDto A_PASS_DTO = new PassDto();
  private SaleDto A_SALE_DTO = new SaleDto();
  private PassService passService;
  private SaleService saleService;

  @Before
  public void setUp() {
    A_PASS_DTO.userId = A_USER_ID;
    A_PASS_DTO.parkingZone = A_PARKING_ZONE;
    A_PASS_DTO.passType = A_PASS_TYPE;

    A_SALE_DTO.passDTO = A_PASS_DTO;
    A_SALE_DTO.deliveryMode = A_DELIVERY_MODE;

    passService = mock(PassService.class);
    saleService = new SaleService(passService);
  }

  @Test
  public void whenCreatingSale_thenShouldCallPassServiceToCreatePass(){
    saleService.createSale(A_SALE_DTO);

    verify(passService).createPass(A_PASS_DTO);
  }
}
