package ca.ulaval.glo4003.spamdul.entity.fundraising;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class InitiativeIdTest {

  @Test
  public void whenCreatingInitiativeId_shouldGenerateNonEmptyId() {
    InitiativeId initiativeId = new InitiativeId();

    assertThat(initiativeId.toString()).isNotEqualTo("");
  }

  @Test
  public void whenComparingDifferentInitiativeId_shouldNotBeEqual() {
    InitiativeId initiativeId = new InitiativeId();
    InitiativeId anotherInitiativeId = new InitiativeId();

    assertThat(initiativeId).isNotEqualTo(anotherInitiativeId);
    assertThat(initiativeId.hashCode()).isNotEqualTo(anotherInitiativeId.hashCode());
  }

  @Test
  public void whenComparingTheSameInitiativeId_shouldBeEqual() {
    InitiativeId initiativeId = new InitiativeId();
    InitiativeId sameInitiativeId = InitiativeId.valueOf(initiativeId.toString());

    assertThat(initiativeId).isEqualTo(sameInitiativeId);
    assertThat(initiativeId.hashCode()).isEqualTo(sameInitiativeId.hashCode());
  }
}
