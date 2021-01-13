package ca.ulaval.glo4003.spamdul.parking2.infrastructure.populators;

import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionInfos;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionInfosRepository;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionType;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.reader.JsonReader;
import java.util.List;

public class InfractionInfosPopulatorJson implements InfractionInfosPopulator {

  private final JsonReader jsonReader;
  private final String path;

  public InfractionInfosPopulatorJson(JsonReader jsonReader, String path) {
    this.jsonReader = jsonReader;
    this.path = path;
  }

  @Override
  public void populate(InfractionInfosRepository infractionInfosRepository) {
    List<InfractionDtoJson> infractionDtos = jsonReader.read(path, InfractionDtoJson[].class);
    infractionDtos.forEach(infractionDto -> {
      InfractionType infractionType = InfractionType.fromCode(infractionDto.code);
      Amount amount = Amount.valueOf(infractionDto.montant);
      InfractionInfos infractionInfos = new InfractionInfos(infractionType, amount, infractionDto.infraction);
      infractionInfosRepository.save(infractionInfos);
    });
  }
}
