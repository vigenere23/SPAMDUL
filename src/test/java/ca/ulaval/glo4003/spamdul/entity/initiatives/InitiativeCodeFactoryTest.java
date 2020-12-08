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
public class InitiativeCodeFactoryTest {

  private static final String A_VALUE = "123";

  @Mock
  private IdGenerator idGenerator;

  private InitiativeCodeFactory initiativeCodeFactory;

  @Before
  public void setUp() {
    initiativeCodeFactory = new InitiativeCodeFactory(idGenerator);
  }

  @Test
  public void whenCreating_shouldReturnFromIdGenerator() {
    when(idGenerator.generateId()).thenReturn(A_VALUE);
    InitiativeCode initiativeCode = initiativeCodeFactory.create();
    assertThat(initiativeCode.toString()).isEqualTo(A_VALUE);
  }
}
