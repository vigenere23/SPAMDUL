package ca.ulaval.glo4003.spamdul.entity.authentication;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Random;


public class TemporaryToken {

  public static final String HASHING_ALGORITHM = "SHA-256";
  private final String token;

  public TemporaryToken() {
    MessageDigest md = null;

    try {
      md = MessageDigest.getInstance(HASHING_ALGORITHM);
    } catch (NoSuchAlgorithmException e) {
      //ignore, hashing algorithm is set in the class
    }
    String text = new Random().doubles().toString();

    md.update(text.getBytes(StandardCharsets.UTF_8));
    byte[] digest = md.digest();

    token = String.format("%064x", new BigInteger(1, digest));
  }

  private TemporaryToken(String value) {
    this.token = value;
  }

  public static TemporaryToken valueOf(String value) {
    if (value.isEmpty()) {
      throw new NoRegisteredUserLoggedInException();
    }

    return new TemporaryToken(value);
  }

  public String toString() {
    return token;
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TemporaryToken token1 = (TemporaryToken) o;
    return Objects.equals(token, token1.token);
  }

  public int hashCode() {
    return Objects.hash(token);
  }
}
