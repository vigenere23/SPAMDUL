package ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto;

public class CampusAccessResponse {

  public String campusAccessCode;

  @Override
  public String toString() {
    return "CampusAccessResponse{" +
        "campusAccessCode='" + campusAccessCode + '\'' +
        '}';
  }
}
