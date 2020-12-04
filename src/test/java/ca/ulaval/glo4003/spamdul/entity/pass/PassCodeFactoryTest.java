package ca.ulaval.glo4003.spamdul.entity.pass;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.ids.IdGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PassCodeFactoryTest {

  private static final Long A_VALUE = 123L;

  @Mock
  private IdGenerator<Long> idGenerator;

  private PassCodeFactory passCodeFactory;

  @Before
  public void setUp() {
    passCodeFactory = new PassCodeFactory(idGenerator);
  }

  @Test
  public void whenCreating_shouldReturnFromIdGenerator() {
    when(idGenerator.getNextId()).thenReturn(A_VALUE);
    PassCode passCode = passCodeFactory.create();
    assertThat(passCode.toString()).isEqualTo(A_VALUE.toString());
  }
}
