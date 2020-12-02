package ca.ulaval.glo4003.spamdul;

@SuppressWarnings("all")
public class SpamdUlMain {

  public static void main(String[] args) throws Exception {
    SpamdUlApplication app = new JettyJerseyApp();
    app.start();
  }
}
