package ca.ulaval.glo4003.spamdul.parking.infrastructure.persistence.infractions;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.parking.entities.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.InfractionInfosDto;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.exceptions.InfractionNotFoundException;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.reader.JsonReader;
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
    List<InfractionInfosDto> infractionInfosDtoList = new ArrayList<>();

    InfractionInfosDto infractionInfosDto = new InfractionInfosDto();
    infractionInfosDto.code = AN_INFRACTION_CODE_STRING;
    infractionInfosDto.infraction = AN_INFRACTION_CODE_DESCRITPION;
    infractionInfosDto.montant = AN_INFRACTION_AMOUNT;
    infractionInfosDtoList.add(infractionInfosDto);

    InfractionInfosDto anotherInfractionInfosDto = new InfractionInfosDto();
    anotherInfractionInfosDto.code = ANOTHER_INFRACTION_CODE_STRING;
    anotherInfractionInfosDto.infraction = ANOTHER_INFRACTION_CODE_DESCRITPION;
    anotherInfractionInfosDto.montant = ANOTHER_INFRACTION_AMOUNT;
    infractionInfosDtoList.add(anotherInfractionInfosDto);

    given(jsonReader.read(A_PATH, InfractionInfosDto[].class)).willReturn(infractionInfosDtoList);
  }

  @Test
  public void whenFindingBy_shouldReadJsonWithJsonReader() {
    repository.findBy(ANOTHER_INFRACTION_CODE);

    verify(jsonReader).read(A_PATH, InfractionInfosDto[].class);
  }

  @Test
  public void whenFindingBy_shouldReturnTheRightInfractionInfos() {
    InfractionInfosDto infractionInfosDto = repository.findBy(AN_INFRACTION_CODE);
    InfractionInfosDto anotherInfractionInfosDto = repository.findBy(ANOTHER_INFRACTION_CODE);

    Truth.assertThat(infractionInfosDto.code).isEqualTo(AN_INFRACTION_CODE_STRING);
    Truth.assertThat(infractionInfosDto.infraction).isEqualTo(AN_INFRACTION_CODE_DESCRITPION);
    Truth.assertThat(infractionInfosDto.montant).isEqualTo(AN_INFRACTION_AMOUNT);

    Truth.assertThat(anotherInfractionInfosDto.code).isEqualTo(ANOTHER_INFRACTION_CODE_STRING);
    Truth.assertThat(anotherInfractionInfosDto.infraction).isEqualTo(ANOTHER_INFRACTION_CODE_DESCRITPION);
    Truth.assertThat(anotherInfractionInfosDto.montant).isEqualTo(ANOTHER_INFRACTION_AMOUNT);
  }

  @Test(expected = InfractionNotFoundException.class)
  public void givenAnInvalidInfractionCode_whenFindingBy_shouldThrowCantFindInfractionException() {
    repository.findBy(InfractionCode.valueOf("Invalid"));
  }
}
