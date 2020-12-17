package ca.ulaval.glo4003.spamdul.shared.entities.ids;

public abstract class Id {

  private final String value;

  protected Id(String value) {
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
    Id otherId = (Id) object;

    return value.equals(otherId.value);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public String toString() {
    return value;
  }
}
