package ca.ulaval.glo4003.spamdul;

import ca.ulaval.glo4003.spamdul.shared.application.JettyJerseyApp;
import ca.ulaval.glo4003.spamdul.shared.application.SpamdUlApplication;
import ca.ulaval.glo4003.spamdul.shared.context.main.ContextFactory;

@SuppressWarnings("all")
public class SpamdUlMain {

  public static void main(String[] args) throws Exception {
    SpamdUlApplication app = new JettyJerseyApp(new ContextFactory());
    app.start();
  }
}
