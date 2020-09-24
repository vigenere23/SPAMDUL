package ca.ulaval.glo4003.spamdul.entity.user;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class UserIdTest {

  @Test
  public void whenCreatingUserId_shouldGenerateAnId() {
    UserId userId = new UserId();

    assertThat(userId.toString()).isNotEqualTo("");
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
    UserId sameUserId = UserId.valueOf(userId.toString());

    assertThat(userId).isEqualTo(sameUserId);
    assertThat(userId.hashCode()).isEqualTo(sameUserId.hashCode());
  }
}