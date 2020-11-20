package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import java.util.Objects;

public class CampusAccessCode {

  private static Long lastId = 0L;
  private final Long id;

  public CampusAccessCode() {
    id = getNextId();
  }

  private CampusAccessCode(long id) {
    this.id = id;
  }

  public static CampusAccessCode valueOf(String userId) {
    try {
      return new CampusAccessCode(Long.parseLong(userId));
    } catch (NumberFormatException e) {
      throw new InvalidCampusAccessCodeFormatException("invalid campus code format");
    }
  }

  private static synchronized Long getNextId() {
    lastId += 1;

    return lastId;
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CampusAccessCode campusAccessCode = (CampusAccessCode) o;

    return Objects.equals(id, campusAccessCode.id);
  }

  @Override public int hashCode() {
    return Objects.hash(id);
  }

  @Override public String toString() {
    return this.id.toString();
  }

}
