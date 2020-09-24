package ca.ulaval.glo4003.spamdul.usecases.pass;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassFactory;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PassServiceTest {
    private final UserId A_USER_ID = new UserId();
    private final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_2;
    private final int A_NUMBER = 2;
    private final LocalDate A_DATE = LocalDate.of(1111, 11, 11);

    private final Pass A_PASS = new Pass(A_USER_ID, A_PARKING_ZONE, A_DATE, A_DATE);;
    @Mock
    private PassFactory passFactory;
    @Mock
    private PassRepository passRepository;
    @Mock
    private UserRepository userRepository;

    private PassService passService;

    @Before
    public void setUp() {
        passService = new PassService(passRepository, userRepository, passFactory);
    }

    @Test
    public void whenSellingPass_shouldFindUserInRepository() {
        passService.createPass(A_USER_ID, A_PARKING_ZONE, A_NUMBER);

        verify(userRepository).findById(A_USER_ID);
    }

    @Test
    public void whenSellingPass_shouldCallFactoryToCreateNewPass() {
        passService.createPass(A_USER_ID, A_PARKING_ZONE, A_NUMBER);

        verify(passFactory).create(A_USER_ID, A_PARKING_ZONE, A_NUMBER);
    }

    @Test
    public void whenSellingPass_shouldAddPassToRepository() {
        when(passFactory.create(A_USER_ID, A_PARKING_ZONE, A_NUMBER)).thenReturn(A_PASS);

        passService.createPass(A_USER_ID, A_PARKING_ZONE, A_NUMBER);

        verify(passRepository).save(A_PASS);
    }
}
