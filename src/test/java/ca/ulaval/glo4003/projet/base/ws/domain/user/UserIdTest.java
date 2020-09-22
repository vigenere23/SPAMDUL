package ca.ulaval.glo4003.projet.base.ws.domain.user;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class UserIdTest {

  @Test
  public void whenCreatingUserId_shouldGenerateAnId() {
    UserId userId = new UserId();

    assertThat(userId.toString()).isEqualTo("1");
  }

  @Test
  public void whenComparingDifferentUserId_shouldNotBeEqual() {
    UserId userId = new UserId();
    UserId anotherUserId = new UserId();

    assertThat(userId).isNotEqualTo(anotherUserId);
    assertThat(userId.hashCode()).isNotEqualTo(anotherUserId.hashCode());
  }

  @Test
  public void whenComparingTheSameUserId_shouldBeEqual() {
    UserId userId = new UserId();
    UserId sameUserId = UserId.valueOf("1");

    assertThat(userId).isEqualTo(sameUserId);
    assertThat(userId.hashCode()).isEqualTo(sameUserId.hashCode());
  }
}