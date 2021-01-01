package ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.charging.entities.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.charging.entities.rechargul.exceptions.RechargULCardNotFoundException;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.User;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserRepository;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.exceptions.UserNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EnoughCreditForChargingVerifierTest {

  public static final RechargULCardId RECHARG_UL_CARD_ID = RechargULCardId.valueOf("123");
  private EnoughCreditForChargingVerifier enoughCreditForChargingVerifier;

  @Mock
  private UserRepository userRepository;
  @Mock
  private User user;

  @Before
  public void setUp() throws Exception {
    enoughCreditForChargingVerifier = new EnoughCreditForChargingVerifier(userRepository);
  }

  @Test
  public void whenVerifying_shouldCallUserToVerifyIfEnoughCredit() {
    when(userRepository.findBy(RECHARG_UL_CARD_ID)).thenReturn(user);

    enoughCreditForChargingVerifier.verify(RECHARG_UL_CARD_ID);

    verify(user).verifyEnoughCreditsForCharging();
  }

  @Test(expected = RechargULCardNotFoundException.class)
  public void givenNoUserAssociatedWithRechargULCard_whenVerifying_shouldThrowException() {
    when(userRepository.findBy(RECHARG_UL_CARD_ID)).thenThrow(UserNotFoundException.class);

    enoughCreditForChargingVerifier.verify(RECHARG_UL_CARD_ID);
  }
}
