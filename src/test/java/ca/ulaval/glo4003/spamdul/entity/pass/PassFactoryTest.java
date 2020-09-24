package ca.ulaval.glo4003.spamdul.entity.pass;

import ca.ulaval.glo4003.spamdul.entity.calendar.Calendar;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PassFactoryTest {
    private final UserId A_USER_ID = new UserId();
    private final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_2;
    private final int A_NUMBER_LEQ_THAN_THREE = 2;
    private final int A_NUMBER_GREATER_THAN_THREE = 4;
    private final LocalDate A_DATE = LocalDate.of(1111,1,1);
    private final LocalDate ANOTHER_DATE = LocalDate.of(2222,2,2);

    @Mock
    private Calendar calendar;

    private PassFactory passFactory;

    @Before
    public void setUpCalendar() {
        when(calendar.getCurrentTermStartDate()).thenReturn(A_DATE);
        when(calendar.getEndOfTermDateInNTerms(A_NUMBER_LEQ_THAN_THREE)).thenReturn(ANOTHER_DATE);

        passFactory = new PassFactory(calendar);
    }

    @Test
    public void whenCreatingPass_shouldGetCurrentTermStartDateFromCalendar() {
        passFactory.create(A_USER_ID, A_PARKING_ZONE, A_NUMBER_LEQ_THAN_THREE);

        verify(calendar).getCurrentTermStartDate();
    }

    @Test
    public void givenNumberOfTerms_whenCreatingPass_shouldGetEndDateOfLastTermFromCalendar() {
        passFactory.create(A_USER_ID, A_PARKING_ZONE, A_NUMBER_LEQ_THAN_THREE);

        verify(calendar).getEndOfTermDateInNTerms(A_NUMBER_LEQ_THAN_THREE);
    }

    @Test
    public void whenCreatingPass_shouldCreatePassWithRightInfo() {
        Pass pass = passFactory.create(A_USER_ID, A_PARKING_ZONE, A_NUMBER_LEQ_THAN_THREE);

        assertThat(pass.getUserId()).isEqualTo(A_USER_ID);
        assertThat(pass.getParkingZone()).isEqualTo(A_PARKING_ZONE);
        assertThat(pass.getFirstDayOfValidity()).isEqualTo(A_DATE);
        assertThat(pass.getLastDayOfValidity()).isEqualTo(ANOTHER_DATE);
    }


    @Test(expected = InvalidNumberOfTermsException.class)
    public void givenMoreThanTreeTerms_whenCreatingPass_shouldThrowInvalidNumberOfTermsException() {
        passFactory.create(A_USER_ID, A_PARKING_ZONE, A_NUMBER_GREATER_THAN_THREE);
    }
}
