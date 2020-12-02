package ca.ulaval.glo4003.spamdul.context.main;

public class MainContextFactory {

  public MainContext create(ContextType contextType) {
    switch (contextType) {
      case DEV:
        return new DevMainContext();
      case PROD:
        return new ProdMainContext();
      case TEST:
        return new TestMainContext();
      default:
        throw new RuntimeException(String.format("context type %s is not yet supported", contextType));
    }
  }
}
