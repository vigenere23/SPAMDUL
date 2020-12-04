package ca.ulaval.glo4003.spamdul.entity.initiatives;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.ids.IdGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InitiativeIdFactoryTest {

  private static final Long A_VALUE = 123L;

  @Mock
  private IdGenerator<Long> idGenerator;

  private InitiativeIdFactory initiativeIdFactory;

  @Before
  public void setUp() {
    initiativeIdFactory = new InitiativeIdFactory(idGenerator);
  }

  @Test
  public void whenCreating_shouldReturnFromIdGenerator() {
    when(idGenerator.getNextId()).thenReturn(A_VALUE);
    InitiativeId initiativeId = initiativeIdFactory.create();
    assertThat(initiativeId.toString()).isEqualTo(A_VALUE.toString());
  }
}
