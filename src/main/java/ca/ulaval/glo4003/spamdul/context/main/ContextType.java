package ca.ulaval.glo4003.spamdul.context.main;

public enum ContextType {
  DEV,
  TEST,
  PROD;

  public static ContextType parse(String contextType) {
    if (contextType == null) {
      return ContextType.TEST;
    }

    return ContextType.valueOf(contextType.toUpperCase());
  }
}
