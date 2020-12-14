package ca.ulaval.glo4003.spamdul.entity.authentication;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class TemporaryTokenTest {

  @Test
  public void whenCreatingNewTemporaryToken_shouldGenerateAToken() {
    TemporaryToken temporaryToken = new TemporaryToken();

    assertThat(temporaryToken.toString()).isNotEmpty();
  }
}
