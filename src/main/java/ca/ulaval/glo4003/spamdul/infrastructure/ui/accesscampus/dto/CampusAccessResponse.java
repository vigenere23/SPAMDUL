package ca.ulaval.glo4003.spamdul.infrastructure.ui.accesscampus.dto;

public class CampusAccessResponse {

  private String campusAccessCode;

  public CampusAccessResponse(String campusAccessCode) {
    this.campusAccessCode = campusAccessCode;
  }

  public String getAccessCode() {
    return campusAccessCode;
  }
}
