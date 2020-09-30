package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccessGrantedObservableTest {

  @Mock
  private AccessGrantedObserver accessGrantedObserver;
  @Mock
  private AccessGrantedObserver accessGrantedObserver2;
  @Mock
  private CampusAccess A_CAMPUS_ACCESS;
  private final LocalDate A_DATE = LocalDate.now();


  private AccessGrantedObservable accessGrantedObservable;

  @Before
  public void setUp() {
    accessGrantedObservable = new AccessGrantedObservable();
  }

  @Test
  public void givenObserverNotRegistered_whenNotifyingObservers_itDoesNotCallThatObserver() {
    accessGrantedObservable.notifyAccessGrantedWithCampusAccess(A_CAMPUS_ACCESS, A_DATE);
    verify(accessGrantedObserver, never()).handleAccessGrantedWithCampusAccess(A_CAMPUS_ACCESS, A_DATE);
  }

  @Test
  public void givenMultipleObserversRegistered_whenNotifyingObservers_itCallsThoseObservers() {
    accessGrantedObservable.register(accessGrantedObserver);
    accessGrantedObservable.register(accessGrantedObserver2);

    accessGrantedObservable.notifyAccessGrantedWithCampusAccess(A_CAMPUS_ACCESS, A_DATE);

    verify(accessGrantedObserver).handleAccessGrantedWithCampusAccess(A_CAMPUS_ACCESS, A_DATE);
    verify(accessGrantedObserver2).handleAccessGrantedWithCampusAccess(A_CAMPUS_ACCESS, A_DATE);
  }

  @Test
  public void givenObserverRegisteredAndThenUnregistered_whenNotifyingObservers_itDoesNotCallThatObserver() {
    accessGrantedObservable.register(accessGrantedObserver);
    accessGrantedObservable.unregister(accessGrantedObserver);

    accessGrantedObservable.notifyAccessGrantedWithCampusAccess(A_CAMPUS_ACCESS, A_DATE);

    verify(accessGrantedObserver, never()).handleAccessGrantedWithCampusAccess(A_CAMPUS_ACCESS, A_DATE);
  }
}
