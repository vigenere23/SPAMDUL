package ca.ulaval.glo4003.spamdul.infrastructure.db.infractions;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.infrastructure.reader.JsonReader;
import com.google.common.truth.Truth;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class InfractionsJsonRepositoryTest {

  private final String A_PATH = "a/path/to/a/json/file";
  private final String AN_INFRACTION_CODE_STRING = "IFN_01";
  private final String ANOTHER_INFRACTION_CODE_STRING = "IFN_02";
  private final String ANOTHER_INFRACTION_CODE_DESCRITPION = "another infraction";
  private final String AN_INFRACTION_CODE_DESCRITPION = "infraction";
  private final int ANOTHER_INFRACTION_AMOUNT = 99;
  private final int AN_INFRACTION_AMOUNT = 100;
  private final InfractionCode AN_INFRACTION_CODE = InfractionCode.valueOf(AN_INFRACTION_CODE_STRING);
  private final InfractionCode ANOTHER_INFRACTION_CODE = InfractionCode.valueOf(ANOTHER_INFRACTION_CODE_STRING);

  private InfractionsJsonRepository repository;
  private JsonReader jsonReader;

  @Before
  public void setUp() throws Exception {
    jsonReader = mock(JsonReader.class);
    repository = new InfractionsJsonRepository(A_PATH, jsonReader);
    List<InfractionDTO> infractionDTOS = new ArrayList<>();

    InfractionDTO infractionDTO = new InfractionDTO();
    infractionDTO.code = AN_INFRACTION_CODE_STRING;
    infractionDTO.infraction = AN_INFRACTION_CODE_DESCRITPION;
    infractionDTO.montant = AN_INFRACTION_AMOUNT;
    infractionDTOS.add(infractionDTO);

    InfractionDTO anotherInfractionDTO = new InfractionDTO();
    anotherInfractionDTO.code = ANOTHER_INFRACTION_CODE_STRING;
    anotherInfractionDTO.infraction = ANOTHER_INFRACTION_CODE_DESCRITPION;
    anotherInfractionDTO.montant = ANOTHER_INFRACTION_AMOUNT;
    infractionDTOS.add(anotherInfractionDTO);

    given(jsonReader.read(A_PATH, InfractionDTO[].class)).willReturn(infractionDTOS);
  }

  @Test
  public void whenFindingBy_shouldReadJsonWithJsonReader() {
    repository.findBy(ANOTHER_INFRACTION_CODE);

    verify(jsonReader, times(1)).read(A_PATH, InfractionDTO[].class);
  }

  @Test
  public void whenFindingBy_shouldReturnTheRightInfraction() {
    Infraction infraction = repository.findBy(AN_INFRACTION_CODE);
    Infraction anotherInfraction = repository.findBy(ANOTHER_INFRACTION_CODE);

    Truth.assertThat(infraction.getCode()).isEqualTo(AN_INFRACTION_CODE);
    Truth.assertThat(infraction.getInfractionDscription()).isEqualTo(AN_INFRACTION_CODE_DESCRITPION);
    Truth.assertThat(infraction.getMontant()).isEqualTo(AN_INFRACTION_AMOUNT);

    Truth.assertThat(anotherInfraction.getCode()).isEqualTo(ANOTHER_INFRACTION_CODE);
    Truth.assertThat(anotherInfraction.getInfractionDscription()).isEqualTo(ANOTHER_INFRACTION_CODE_DESCRITPION);
    Truth.assertThat(anotherInfraction.getMontant()).isEqualTo(ANOTHER_INFRACTION_AMOUNT);
  }

  @Test(expected = CantFindInfractionException.class)
  public void givenAnInvalidInfractionCode_whenFindingBy_shouldThrowCantFindInfractionException() {
    repository.findBy(InfractionCode.valueOf("Invalid"));
  }
}