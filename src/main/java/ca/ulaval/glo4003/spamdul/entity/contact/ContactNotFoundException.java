package ca.ulaval.glo4003.spamdul.entity.contact;

public class ContactNotFoundException extends Exception {

  private static final long serialVersionUID = -898705420292326863L;

  public ContactNotFoundException(String message) {
    super(message);
  }

}
