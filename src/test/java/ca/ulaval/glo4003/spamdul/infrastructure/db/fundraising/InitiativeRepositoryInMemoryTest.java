package ca.ulaval.glo4003.spamdul.infrastructure.db.fundraising;

import ca.ulaval.glo4003.spamdul.entity.fundraising.Initiative;
import ca.ulaval.glo4003.spamdul.entity.fundraising.InitiativeFactory;
import com.google.common.truth.Truth;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class InitiativeRepositoryInMemoryTest {

  private InitiativeRepositoryInMemory initiativeRepositoryInMemory;
  private final String A_NAME = "dfhsadf";
  private final double AN_AMOUNT = 21438.23;

  @Before
  public void setUp() {
    initiativeRepositoryInMemory = new InitiativeRepositoryInMemory();
  }

  @Test
  public void givenNewRepository_whenFindingAll_shouldReturnEmptyList() {
    List<Initiative> initiatives = initiativeRepositoryInMemory.findAll();
    Truth.assertThat(initiatives).isEmpty();
  }

  @Test
  public void givenInitiative_whenSaving_shouldBePresent() {
    Initiative initiative = new InitiativeFactory().create(A_NAME, AN_AMOUNT);

    initiativeRepositoryInMemory.save(initiative);

    List<Initiative> initiatives = initiativeRepositoryInMemory.findAll();
    Truth.assertThat(initiatives).containsExactly(initiative);
  }

  @Test
  public void givenMultipleSavedInitiative_whenFindingAll_shouldAllBePresent() {
    Initiative initiative1 = new InitiativeFactory().create(A_NAME, AN_AMOUNT);
    Initiative initiative2 = new InitiativeFactory().create(A_NAME, AN_AMOUNT);
    initiativeRepositoryInMemory.save(initiative1);
    initiativeRepositoryInMemory.save(initiative2);

    List<Initiative> initiatives = initiativeRepositoryInMemory.findAll();

    Truth.assertThat(initiatives).containsExactly(initiative1, initiative2);
  }
}
