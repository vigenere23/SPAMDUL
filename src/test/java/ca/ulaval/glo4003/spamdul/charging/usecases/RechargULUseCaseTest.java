package ca.ulaval.glo4003.spamdul.charging.usecases;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.charging.entities.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.charging.entities.rechargul.RechargULCardFactory;
import ca.ulaval.glo4003.spamdul.charging.entities.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.charging.entities.rechargul.exceptions.RechargULCardNotFoundException;
import ca.ulaval.glo4003.spamdul.charging.usecases.assembler.RechargULDtoAssembler;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.User;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserId;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserRepository;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RechargULUseCaseTest {

  public static final RechargULCardId RECHARG_UL_CARD_ID = RechargULCardId.valueOf("123");
  public static final Amount AMOUNT = Amount.valueOf(10);
  public static final UserId USER_ID = UserId.valueOf("123");
  private RechargULUseCase rechargULUseCase;
  private RechargULCard rechargULCard;
  private TransactionFactory transactionFactory;

  @Mock
  private UserRepository userRepository;
  @Mock
  private User user;
  @Mock
  private RechargULCardFactory rechargULCardFactory;
  @Mock
  private RechargULDtoAssembler rechargULDtoAssembler;

  @Before
  public void setUp() {
    transactionFactory = new TransactionFactory();
    rechargULUseCase = new RechargULUseCase(userRepository, rechargULCardFactory, rechargULDtoAssembler);
    rechargULCard = new RechargULCard(RECHARG_UL_CARD_ID, transactionFactory);

    when(userRepository.findBy(RECHARG_UL_CARD_ID)).thenReturn(user);
  }

  @Test
  public void whenGettingRechargULCard_shouldFindUserAssociatedWithCardInUserRepository() {
    rechargULUseCase.getRechargULCard(RECHARG_UL_CARD_ID);

    verify(userRepository).findBy(RECHARG_UL_CARD_ID);
  }

  @Test(expected = RechargULCardNotFoundException.class)
  public void givenNoUserAssociatedToRechargULCardId_whenGettingRechargULCard_shouldThrowException() {
    doThrow(UserNotFoundException.class).when(userRepository).findBy(RECHARG_UL_CARD_ID);

    rechargULUseCase.getRechargULCard(RECHARG_UL_CARD_ID);
  }

  @Test
  public void whenAddingCreditToRechargULCard_shouldAskUserToAddCreditsToCard() {
    rechargULUseCase.addCredits(RECHARG_UL_CARD_ID, AMOUNT);

    verify(user).addRechargULCredits(AMOUNT);
    verify(userRepository).save(user);
  }

  @Test
  public void whenCreatingCard_shouldCreateCardAndAssociateItToUser() {
    when(rechargULCardFactory.create()).thenReturn(rechargULCard);
    when(userRepository.findBy(USER_ID)).thenReturn(user);

    rechargULUseCase.createCard(USER_ID);

    verify(rechargULCardFactory).create();
    verify(user).associate(rechargULCard);
    verify(userRepository).save(user);
  }
}
