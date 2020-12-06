package ca.ulaval.glo4003.spamdul.infrastructure.db.infractions;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionInfos;
import ca.ulaval.glo4003.spamdul.infrastructure.reader.JsonReader;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import com.google.common.truth.Truth;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class InfractionsInfosJsonRepositoryTest {

  private final String A_PATH = "a/path/to/a/json/file";
  private final String AN_INFRACTION_CODE_STRING = "IFN_01";
  private final String ANOTHER_INFRACTION_CODE_STRING = "IFN_02";
  private final String ANOTHER_INFRACTION_CODE_DESCRITPION = "another infraction";
  private final String AN_INFRACTION_CODE_DESCRITPION = "infraction";
  private final double ANOTHER_INFRACTION_AMOUNT = 99;
  private final double AN_INFRACTION_AMOUNT = 100;
  private final InfractionCode AN_INFRACTION_CODE = InfractionCode.valueOf(AN_INFRACTION_CODE_STRING);
  private final InfractionCode ANOTHER_INFRACTION_CODE = InfractionCode.valueOf(ANOTHER_INFRACTION_CODE_STRING);

  private InfractionsInfosJsonRepository repository;
  private JsonReader jsonReader;

  @Before
  public void setUp() throws Exception {
    jsonReader = mock(JsonReader.class);
    repository = new InfractionsInfosJsonRepository(A_PATH, jsonReader);
    List<InfractionInfos> infractionInfosList = new ArrayList<>();

    InfractionInfos infractionInfos = new InfractionInfos();
    infractionInfos.code = AN_INFRACTION_CODE_STRING;
    infractionInfos.infraction = AN_INFRACTION_CODE_DESCRITPION;
    infractionInfos.montant = AN_INFRACTION_AMOUNT;
    infractionInfosList.add(infractionInfos);

    InfractionInfos anotherInfractionInfos = new InfractionInfos();
    anotherInfractionInfos.code = ANOTHER_INFRACTION_CODE_STRING;
    anotherInfractionInfos.infraction = ANOTHER_INFRACTION_CODE_DESCRITPION;
    anotherInfractionInfos.montant = ANOTHER_INFRACTION_AMOUNT;
    infractionInfosList.add(anotherInfractionInfos);

    given(jsonReader.read(A_PATH, InfractionInfos[].class)).willReturn(infractionInfosList);
  }

  @Test
  public void whenFindingBy_shouldReadJsonWithJsonReader() {
    repository.findBy(ANOTHER_INFRACTION_CODE);

    verify(jsonReader, times(1)).read(A_PATH, InfractionInfos[].class);
  }

  @Test
  public void whenFindingBy_shouldReturnTheRightInfractionInfos() {
    InfractionInfos infractionInfos = repository.findBy(AN_INFRACTION_CODE);
    InfractionInfos anotherInfractionInfos = repository.findBy(ANOTHER_INFRACTION_CODE);

    Truth.assertThat(infractionInfos.code).isEqualTo(AN_INFRACTION_CODE_STRING);
    Truth.assertThat(infractionInfos.infraction).isEqualTo(AN_INFRACTION_CODE_DESCRITPION);
    Truth.assertThat(infractionInfos.montant).isEqualTo(AN_INFRACTION_AMOUNT);

    Truth.assertThat(anotherInfractionInfos.code).isEqualTo(ANOTHER_INFRACTION_CODE_STRING);
    Truth.assertThat(anotherInfractionInfos.infraction).isEqualTo(ANOTHER_INFRACTION_CODE_DESCRITPION);
    Truth.assertThat(anotherInfractionInfos.montant).isEqualTo(ANOTHER_INFRACTION_AMOUNT);
  }

  @Test(expected = CantFindInfractionException.class)
  public void givenAnInvalidInfractionCode_whenFindingBy_shouldThrowCantFindInfractionException() {
    repository.findBy(InfractionCode.valueOf("Invalid"));
  }
}