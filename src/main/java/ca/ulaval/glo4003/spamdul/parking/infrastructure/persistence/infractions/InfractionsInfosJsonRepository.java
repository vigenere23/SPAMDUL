package ca.ulaval.glo4003.spamdul.parking.infrastructure.persistence.infractions;

import ca.ulaval.glo4003.spamdul.parking.entities.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.InfractionInfoRepository;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.InfractionInfosDto;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.exceptions.InfractionNotFoundException;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.reader.JsonReader;
import java.util.List;

public class InfractionsInfosJsonRepository implements InfractionInfoRepository {

  private final String JSON_PATH;
  private final JsonReader reader;

  public InfractionsInfosJsonRepository(String path, JsonReader jsonReader) {
    JSON_PATH = path;
    reader = jsonReader;
  }

  @Override public InfractionInfosDto findBy(InfractionCode infractionCode) {
    List<InfractionInfosDto> infractionInfosDtoList = reader.read(JSON_PATH, InfractionInfosDto[].class);

    for (InfractionInfosDto infractionInfosDto : infractionInfosDtoList) {
      if (infractionCode.equals(InfractionCode.valueOf(infractionInfosDto.code))) {
        return infractionInfosDto;
      }
    }

    throw new InfractionNotFoundException();
  }

}
