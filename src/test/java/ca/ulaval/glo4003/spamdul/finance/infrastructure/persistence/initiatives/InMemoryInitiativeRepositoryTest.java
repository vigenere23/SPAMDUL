package ca.ulaval.glo4003.spamdul.finance.infrastructure.persistence.initiatives;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.Initiative;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.InitiativeCode;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.InitiativeId;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class InMemoryInitiativeRepositoryTest {

  private InMemoryInitiativeRepository inMemoryInitiativeRepository;
  private final String A_NAME = "dfhsadf";
  private final InitiativeCode A_CODE = InitiativeCode.valueOf("asdhasd");
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
    Initiative initiative = new Initiative(InitiativeId.valueOf("123"), A_CODE, A_NAME, AN_AMOUNT);

    inMemoryInitiativeRepository.save(initiative);

    List<Initiative> initiatives = inMemoryInitiativeRepository.findAll();
    assertThat(initiatives).containsExactly(initiative);
  }

  @Test
  public void givenMultipleSavedInitiative_whenFindingAll_shouldAllBePresent() {
    Initiative initiative1 = new Initiative(InitiativeId.valueOf("123"), A_CODE, A_NAME, AN_AMOUNT);
    Initiative initiative2 = new Initiative(InitiativeId.valueOf("456"), A_CODE, A_NAME, AN_AMOUNT);
    inMemoryInitiativeRepository.save(initiative1);
    inMemoryInitiativeRepository.save(initiative2);

    List<Initiative> initiatives = inMemoryInitiativeRepository.findAll();

    assertThat(initiatives).containsExactly(initiative1, initiative2);
  }
}
