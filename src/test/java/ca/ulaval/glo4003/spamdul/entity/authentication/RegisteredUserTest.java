package ca.ulaval.glo4003.spamdul.entity.authentication;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class RegisteredUserTest {

  public static final String A_USERNAME = "username";
  public static final String AN_HASHED_PASSWORD = "password";
  public static final AccessLevel AN_ACCESS_LEVEL = AccessLevel.ADMIN;
  public static final AccessLevel ANOTHER_ACCESS_LEVEL = AccessLevel.SSP_AGENT;

  @Test
  public void whenGeneratingTemporaryToken_shouldCreateNewToken() {
    RegisteredUser registeredUser = new RegisteredUser(A_USERNAME, AN_ACCESS_LEVEL);

    TemporaryToken temporaryToken = registeredUser.generateTemporaryToken();

    assertThat(temporaryToken).isNotNull();
  }

  @Test
  public void givenTheSameAccessLevel_whenVerifyingIfHasTheRightAccessLevel_thenShouldReturnTrue() {
    RegisteredUser registeredUser = new RegisteredUser(A_USERNAME, AN_ACCESS_LEVEL);

    boolean hasTheRightAccessLevel = registeredUser.hasTheRightAccessLevel(AN_ACCESS_LEVEL);

    assertThat(hasTheRightAccessLevel).isTrue();
  }

  @Test
  public void givenAnotherSameAccessLevel_whenVerifyingIfHasTheRightAccessLevel_thenShouldReturnTrue() {
    RegisteredUser registeredUser = new RegisteredUser(A_USERNAME, AN_ACCESS_LEVEL);

    boolean hasTheRightAccessLevel = registeredUser.hasTheRightAccessLevel(ANOTHER_ACCESS_LEVEL);

    assertThat(hasTheRightAccessLevel).isFalse();
  }
}