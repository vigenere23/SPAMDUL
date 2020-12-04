package ca.ulaval.glo4003.spamdul.entity.user;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class UserIdTest {

  @Test
  public void givenValue_whenCreatingUserId_shouldCreateIdWithGivenValue() {
    String value = "1234";
    UserId userId = UserId.valueOf(value);
    assertThat(userId.toString()).isEqualTo(value);
  }

  @Test
  public void whenComparingDifferentUserId_shouldNotBeEqual() {
    UserId userId = UserId.valueOf("1234");
    UserId anotherUserId = UserId.valueOf("5678");

    assertThat(userId).isNotEqualTo(anotherUserId);
    assertThat(userId.hashCode()).isNotEqualTo(anotherUserId.hashCode());
  }

  @Test
  public void whenComparingTheSameUserId_shouldBeEqual() {
    UserId userId = UserId.valueOf("1234");
    UserId sameUserId = UserId.valueOf(userId.toString());

    assertThat(userId).isEqualTo(sameUserId);
    assertThat(userId.hashCode()).isEqualTo(sameUserId.hashCode());
  }
}
