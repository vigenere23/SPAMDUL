package ca.ulaval.glo4003.spamdul.entity.initiatives;

import ca.ulaval.glo4003.spamdul.entity.idgenerator.IdGenerator;
import ca.ulaval.glo4003.spamdul.entity.idgenerator.IncrementalLongIdGenerator;
import java.util.Objects;

public class InitiativeCode {

  private final String code;
  private static final IdGenerator<Long> idGenerator = new IncrementalLongIdGenerator();


  public InitiativeCode(String code) {
    this.code = code;
  }

  public InitiativeCode(ReservedInitiativeCode code) {
    this.code = code.getValue().toString();
  }

  public InitiativeCode() {
    this.code = idGenerator.getNextId().toString();
  }

  @Override
  public String toString() {
    return code;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    InitiativeCode other = (InitiativeCode) object;

    return Objects.equals(this.code, other.code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code);
  }
}
