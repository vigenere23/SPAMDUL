package ca.ulaval.glo4003.spamdul.parking.api.campusaccess.dto;

public class CampusAccessResponse {

  public String campusAccessCode;

  @Override
  public String toString() {
    return "CampusAccessResponse{" +
        "campusAccessCode='" + campusAccessCode + '\'' +
        '}';
  }
}
