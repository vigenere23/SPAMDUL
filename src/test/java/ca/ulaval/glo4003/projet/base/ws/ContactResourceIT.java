package ca.ulaval.glo4003.projet.base.ws;

import static io.restassured.RestAssured.get;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.projet.base.ProjetBaseMain;

@RunWith(MockitoJUnitRunner.class)
public class ContactResourceIT {

  @Before
  public void setUp()
          throws Exception {
    Thread t = new Thread() {
      public void run() {
        try {
          ProjetBaseMain.main(new String[] {});
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    };
    t.setDaemon(true);
    t.start();
  }

  @Test
  public void givenContacts_whenGetAllContacts_thenContactsReturned() {
    get("/api/telephony/contacts").then().body("name", Matchers.hasItem("Steve Jobs"));
  }
}
