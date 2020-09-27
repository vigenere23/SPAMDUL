package ca.ulaval.glo4003.spamdul.usecases.sale;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassType;
import ca.ulaval.glo4003.spamdul.entity.post.PostalAddress;
import ca.ulaval.glo4003.spamdul.entity.sale.DeliveryMode;
import ca.ulaval.glo4003.spamdul.entity.user.Gender;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.usecases.email.EmailService;
import ca.ulaval.glo4003.spamdul.usecases.pass.PassDto;
import ca.ulaval.glo4003.spamdul.usecases.pass.PassService;
import ca.ulaval.glo4003.spamdul.usecases.post.PostalService;
import java.time.DayOfWeek;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class SaleServiceTest {

  private final UserId A_USER_ID = new UserId();
  private final User A_USER = new User(A_USER_ID, "Bob Ross", Gender.MALE, LocalDate.of(2004, 1, 1), DayOfWeek.MONDAY);
  private final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_2;
  private final PassType A_PASS_TYPE = PassType.MONTHLY;
  private final DeliveryMode A_EMAIL_DELIVERY_MODE = DeliveryMode.EMAIL;
  private final DeliveryMode A_POSTAL_DELIVERY_MODE = DeliveryMode.POST;
  private final String A_EMAIL_ADDRESS = "test@test.com";
  private final PostalAddress A_POSTAL_ADDRESS = new PostalAddress("test address");
  private final PassCode A_PASS_CODE = new PassCode();
  private PassDto A_PASS_DTO = new PassDto();
  private SaleDto A_SALE_DTO_BY_EMAIL = new SaleDto();
  private SaleDto A_SALE_DTO_BY_POST = new SaleDto();
  private PassService passService;
  private UserRepository userRepository;
  private EmailService emailService;
  private PostalService postalService;
  private SaleService saleService;

  @Before
  public void setUp() {
    A_PASS_DTO.userId = A_USER_ID;
    A_PASS_DTO.parkingZone = A_PARKING_ZONE;
    A_PASS_DTO.passType = A_PASS_TYPE;

    A_SALE_DTO_BY_EMAIL.passDTO = A_PASS_DTO;
    A_SALE_DTO_BY_EMAIL.deliveryMode = A_EMAIL_DELIVERY_MODE;
    A_SALE_DTO_BY_EMAIL.emailAddress = A_EMAIL_ADDRESS;

    A_SALE_DTO_BY_POST.passDTO = A_PASS_DTO;
    A_SALE_DTO_BY_POST.deliveryMode = A_POSTAL_DELIVERY_MODE;
    A_SALE_DTO_BY_POST.postalAddress = A_POSTAL_ADDRESS;

    passService = mock(PassService.class);
    userRepository = mock(UserRepository.class);
    emailService = mock(EmailService.class);
    postalService = mock(PostalService.class);

    given(passService.createPass(A_PASS_DTO)).willReturn(A_PASS_CODE);
    given(userRepository.findById(A_USER_ID)).willReturn(A_USER);

    saleService = new SaleService(passService, userRepository, emailService, postalService);
  }

  @Test
  public void whenCreatingSale_thenShouldCallPassServiceToCreatePass() {
    saleService.createSale(A_SALE_DTO_BY_EMAIL);

    verify(passService).createPass(A_PASS_DTO);
  }

  @Test
  public void whenCreatingSaleWithEmailDelivery_thenShouldCallEmailService() {
    saleService.createSale(A_SALE_DTO_BY_EMAIL);

    verify(emailService).send(eq(A_EMAIL_ADDRESS), any(String.class), any(String.class));
  }

  @Test
  public void whenCreatingSaleWithPostalDelivery_thenShouldCallPostalService() {
    saleService.createSale(A_SALE_DTO_BY_POST);

    verify(postalService).send(eq(A_USER.getName()), eq(A_POSTAL_ADDRESS), any(String.class));
  }

}
