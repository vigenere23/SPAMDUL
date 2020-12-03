package ca.ulaval.glo4003.spamdul.context.main;

public class ContextFactory {

  public MainContext create(ContextType contextType) {
    switch (contextType) {
      case DEV:
        return new DevContext();
      case PROD:
        return new ProdContext();
      case TEST:
        return new TestContext();
      default:
        throw new RuntimeException(String.format("context type %s is not yet supported", contextType));
    }
  }
}
