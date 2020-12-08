package ca.ulaval.glo4003.spamdul.entity.ids;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Before;
import org.junit.Test;

public class IncrementalIdGeneratorTest {

  private IncrementalIdGenerator idGenerator;

  @Before
  public void setUp() {
    idGenerator = new IncrementalIdGenerator();
  }

  @Test
  public void givenAnIdGenerated_whenGeneratingNextId_shouldBeOneUnitIncrement() {
    String firstId = idGenerator.generateId();
    String nextId = idGenerator.generateId();
    assertThat(Long.parseLong(nextId)).isEqualTo(Long.parseLong(firstId) + 1);
  }
}
