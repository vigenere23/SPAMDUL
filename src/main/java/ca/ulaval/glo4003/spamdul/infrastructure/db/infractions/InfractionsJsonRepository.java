package ca.ulaval.glo4003.spamdul.infrastructure.db.infractions;

import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.reader.JsonReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfractionsJsonRepository implements InfractionRepository {

  private final String JSON_PATH;
  private final JsonReader reader;

  public InfractionsJsonRepository(String path, JsonReader jsonReader) {
    JSON_PATH = path;
    reader = jsonReader;
  }

  public Infraction findBy(InfractionCode infractionCode) {
    List<InfractionDTO> infractionDTOs = reader.read(JSON_PATH, InfractionDTO[].class);
    Map<InfractionCode, Infraction> infractions = generateInfractions(infractionDTOs);

    Infraction infraction = infractions.get(infractionCode);

    if (infraction == null) {
      throw new CantFindInfractionException("The provided infraction code does not correspond to any infraction");
    }

    return infractions.get(infractionCode);
  }

  private Map<InfractionCode, Infraction> generateInfractions(List<InfractionDTO> infractionDTOs) {
    Map<InfractionCode, Infraction> infractions = new HashMap<>();

    for (InfractionDTO infractionDto : infractionDTOs) {
      InfractionCode infractionCode = InfractionCode.valueOf(infractionDto.code);
      Infraction infraction = new Infraction(infractionDto.infraction, infractionCode, infractionDto.montant);
      infractions.put(infractionCode, infraction);
    }

    return infractions;
  }
}
