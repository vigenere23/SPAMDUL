package ca.ulaval.glo4003.spamdul.entity.idgenerator;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Before;
import org.junit.Test;

public class IncrementalLongIdGeneratorTest {

  private IncrementalLongIdGenerator idGenerator;

  @Before
  public void setUp() {
    idGenerator = new IncrementalLongIdGenerator();
  }

  @Test
  public void givenAnIdGenerated_whenGeneratingNextId_shouldBeOneUnitIncrement() {
    Long firstId = idGenerator.getNextId();
    Long nextId = idGenerator.getNextId();
    assertThat(nextId).isEqualTo(firstId + 1);
  }
}