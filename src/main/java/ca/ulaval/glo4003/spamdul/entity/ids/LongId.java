package ca.ulaval.glo4003.spamdul.entity.ids;

public abstract class LongId {

  protected final Long value;

  protected LongId(Long value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    LongId otherId = (LongId) object;

    return value.equals(otherId.value);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
