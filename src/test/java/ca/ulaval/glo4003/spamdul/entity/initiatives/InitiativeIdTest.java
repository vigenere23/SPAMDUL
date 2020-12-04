package ca.ulaval.glo4003.spamdul.entity.initiatives;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class InitiativeIdTest {

  @Test
  public void whenCreatingInitiativeId_shouldGenerateNonEmptyId() {
    InitiativeId initiativeId = InitiativeId.valueOf("123");

    assertThat(initiativeId.toString()).isNotEqualTo("");
  }

  @Test
  public void whenComparingDifferentInitiativeId_shouldNotBeEqual() {
    InitiativeId initiativeId = InitiativeId.valueOf("123");
    InitiativeId anotherInitiativeId = InitiativeId.valueOf("456");

    assertThat(initiativeId).isNotEqualTo(anotherInitiativeId);
    assertThat(initiativeId.hashCode()).isNotEqualTo(anotherInitiativeId.hashCode());
  }

  @Test
  public void whenComparingTheSameInitiativeId_shouldBeEqual() {
    InitiativeId initiativeId = InitiativeId.valueOf("123");
    InitiativeId sameInitiativeId = InitiativeId.valueOf(initiativeId.toString());

    assertThat(initiativeId).isEqualTo(sameInitiativeId);
    assertThat(initiativeId.hashCode()).isEqualTo(sameInitiativeId.hashCode());
  }
}
