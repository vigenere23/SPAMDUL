package ca.ulaval.glo4003.spamdul.usecases.charging;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardFactory;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.RechargULCardNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RechargULServiceTest {

  public static final RechargULCardId RECHARG_UL_CARD_ID = RechargULCardId.valueOf("123");
  public static final Amount AMOUNT = Amount.valueOf(10);
  public static final UserId USER_ID = UserId.valueOf("123");
  private RechargULService rechargULService;
  private RechargULCard rechargULCard;
  private TransactionFactory transactionFactory;

  @Mock
  private UserRepository userRepository;
  @Mock
  private User user;
  @Mock
  private RechargULCardFactory rechargULCardFactory;

  @Before
  public void setUp() {
    transactionFactory = new TransactionFactory();
    rechargULService = new RechargULService(userRepository, rechargULCardFactory);
    rechargULCard = new RechargULCard(RECHARG_UL_CARD_ID, transactionFactory);

    when(userRepository.findBy(RECHARG_UL_CARD_ID)).thenReturn(user);
  }

  @Test
  public void whenGettingRechargULCard_shouldFindUserAssociatedWithCardInUserRepository() {
    rechargULService.getRechargULCard(RECHARG_UL_CARD_ID);

    verify(userRepository, times(1)).findBy(RECHARG_UL_CARD_ID);
  }

  @Test(expected = RechargULCardNotFoundException.class)
  public void givenNoUserAssociatedToREchargULCardId_whenGettingRechargULCard_shouldThrowException() {
    when(userRepository.findBy(RECHARG_UL_CARD_ID)).thenThrow(UserNotFoundException.class);

    rechargULService.getRechargULCard(RECHARG_UL_CARD_ID);
  }

  @Test
  public void whenAddingCreditToRechargULCard_shouldAskUserToAddCreditsToCard() {
    rechargULService.addCredits(RECHARG_UL_CARD_ID, AMOUNT);

    verify(user, times(1)).addRechargULCredits(AMOUNT);
    verify(userRepository, times(1)).save(user);
  }

  @Test
  public void whenCreatingCard_shouldCreateCardAndAssociateItToUser() {
    when(rechargULCardFactory.create()).thenReturn(rechargULCard);
    when(userRepository.findBy(USER_ID)).thenReturn(user);

    rechargULService.createCard(USER_ID);

    verify(rechargULCardFactory, times(1)).create();
    verify(user, times(1)).associate(rechargULCard);
    verify(userRepository, times(1)).save(user);
  }
}
