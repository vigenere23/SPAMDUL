package ca.ulaval.glo4003.spamdul.infrastructure.db.fundraising;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.initiatives.Initiative;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeFactory;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class InMemoryInitiativeRepositoryTest {

  private InMemoryInitiativeRepository inMemoryInitiativeRepository;
  private final String A_NAME = "dfhsadf";
  private final Amount AN_AMOUNT = Amount.valueOf(21438.23);

  @Before
  public void setUp() {
    inMemoryInitiativeRepository = new InMemoryInitiativeRepository();
  }

  @Test
  public void givenNewRepository_whenFindingAll_shouldReturnEmptyList() {
    List<Initiative> initiatives = inMemoryInitiativeRepository.findAll();
    assertThat(initiatives).isEmpty();
  }

  @Test
  public void givenInitiative_whenSaving_shouldBePresent() {
    Initiative initiative = new InitiativeFactory().create(A_NAME, AN_AMOUNT);

    inMemoryInitiativeRepository.save(initiative);

    List<Initiative> initiatives = inMemoryInitiativeRepository.findAll();
    assertThat(initiatives).containsExactly(initiative);
  }

  @Test
  public void givenMultipleSavedInitiative_whenFindingAll_shouldAllBePresent() {
    Initiative initiative1 = new InitiativeFactory().create(A_NAME, AN_AMOUNT);
    Initiative initiative2 = new InitiativeFactory().create(A_NAME, AN_AMOUNT);
    inMemoryInitiativeRepository.save(initiative1);
    inMemoryInitiativeRepository.save(initiative2);

    List<Initiative> initiatives = inMemoryInitiativeRepository.findAll();

    assertThat(initiatives).containsExactly(initiative1, initiative2);
  }
}
