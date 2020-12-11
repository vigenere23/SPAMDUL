package ca.ulaval.glo4003.spamdul.ui.infractions.dto;

import java.util.Objects;

public class InfractionResponse {

  public String infractionId;
  public String code;
  public String reason;
  public double amount;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InfractionResponse that = (InfractionResponse) o;
    return Double.compare(that.amount, amount) == 0 &&
        Objects.equals(infractionId, that.infractionId) &&
        Objects.equals(code, that.code) &&
        Objects.equals(reason, that.reason);
  }

  @Override
  public int hashCode() {
    return Objects.hash(infractionId, code, reason, amount);
  }
}
