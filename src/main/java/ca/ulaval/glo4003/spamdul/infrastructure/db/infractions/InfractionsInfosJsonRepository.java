package ca.ulaval.glo4003.spamdul.infrastructure.db.infractions;

import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionInfoRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionInfosDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionNotFoundException;
import ca.ulaval.glo4003.spamdul.infrastructure.reader.JsonReader;
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
