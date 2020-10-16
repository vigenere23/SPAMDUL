package ca.ulaval.glo4003.spamdul.infrastructure.reader;

import ca.ulaval.glo4003.spamdul.infrastructure.db.infractions.InfractionDTO;
import com.google.common.truth.Truth;
import java.util.List;
import org.junit.Test;

public class JsonReaderTest {

  @Test
  public void givenAValidJsonFilePath_whenReading_shouldReturnDtos() {
    JsonReader reader = new JsonReader();

    List<InfractionDTO> dtos = reader.read("src/test/resources/json_test.json", InfractionDTO[].class);

    System.out.println(dtos);

    Truth.assertThat(dtos.get(0).code).isEqualTo("ZONE_01");
    Truth.assertThat(dtos.get(0).infraction).isEqualTo("mauvaise zone");
    Truth.assertThat(dtos.get(0).montant).isEqualTo(55);

    Truth.assertThat(dtos.get(1).code).isEqualTo("VIG_01");
    Truth.assertThat(dtos.get(1).infraction).isEqualTo("vignette pas admissible pour la journée");
    Truth.assertThat(dtos.get(1).montant).isEqualTo(22);
  }

  @Test(expected = InvalidJsonFile.class)
  public void givenAnInvalidJsonFilePath_whenReading_shouldThrowCantReadFileFromPathException() {
    new JsonReader().read("invalid/path", InfractionDTO[].class);
  }
}
