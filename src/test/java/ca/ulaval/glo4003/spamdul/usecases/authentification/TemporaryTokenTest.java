package ca.ulaval.glo4003.spamdul.usecases.authentification;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import org.junit.Test;

public class TemporaryTokenTest {

  @Test
  public void whenCreatingNewTemporaryToken_shouldGenerateAToken() {
    TemporaryToken temporaryToken = new TemporaryToken();

    assertThat(temporaryToken.toString()).isNotEmpty();
  }
}