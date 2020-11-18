package ca.ulaval.glo4003.spamdul.entity.authentication;

import ca.ulaval.glo4003.spamdul.usecases.authentification.TemporaryToken;
import java.util.Objects;

public class RegisteredUser {

  private String username;
  private AccessLevel accessLevel;

  public RegisteredUser(String username, AccessLevel accessLevel) {
    this.username = username;
    this.accessLevel = accessLevel;
  }

  public TemporaryToken generateTemporaryToken() {
    return new TemporaryToken();
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RegisteredUser that = (RegisteredUser) o;
    return Objects.equals(username, that.username) &&
        accessLevel == that.accessLevel;
  }

  public int hashCode() {
    return Objects.hash(username, accessLevel);
  }

  public boolean hasTheRightAccessLevel(AccessLevel accessLevel) {
    return this.accessLevel == accessLevel;
  }
}
