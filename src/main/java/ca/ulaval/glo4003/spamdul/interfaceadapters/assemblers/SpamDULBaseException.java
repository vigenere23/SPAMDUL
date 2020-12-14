package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers;

public abstract class SpamDULBaseException extends RuntimeException {

  public abstract String getError();

  public abstract String getDescription();

  public abstract int getStatus();
}
