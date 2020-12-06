package ca.ulaval.glo4003.spamdul.entity.parking.campusaccess;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.ids.IdGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CampusAccessCodeFactoryTest {

  private static final String A_VALUE = "123";

  @Mock
  private IdGenerator idGenerator;

  private CampusAccessCodeFactory campusAccessCodeFactory;

  @Before
  public void setUp() {
    campusAccessCodeFactory = new CampusAccessCodeFactory(idGenerator);
  }

  @Test
  public void whenCreating_shouldReturnFromIdGenerator() {
    when(idGenerator.generateId()).thenReturn(A_VALUE);
    CampusAccessCode campusAccessCode = campusAccessCodeFactory.create();
    assertThat(campusAccessCode.toString()).isEqualTo(A_VALUE);
  }
}
