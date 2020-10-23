package ca.ulaval.glo4003.spamdul.infrastructure.db.infractions;

import ca.ulaval.glo4003.spamdul.entity.infractions.*;
import ca.ulaval.glo4003.spamdul.infrastructure.reader.JsonReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfractionsInfosJsonRepository implements InfractionInfoRepository {

  private final String JSON_PATH;
  private final JsonReader reader;

  public InfractionsInfosJsonRepository(String path, JsonReader jsonReader) {
    JSON_PATH = path;
    reader = jsonReader;
  }

  @Override public InfractionInfos findBy(InfractionCode infractionCode) {
    List<InfractionInfos> infractionInfosList = reader.read(JSON_PATH, InfractionInfos[].class);

    for (InfractionInfos infractionInfos : infractionInfosList) {
      if (infractionCode.equals(InfractionCode.valueOf(infractionInfos.code))) return infractionInfos;
    }

    throw new CantFindInfractionException("The provided infraction code does not correspond to any infraction");
  }

}
