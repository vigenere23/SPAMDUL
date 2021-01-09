package ca.ulaval.glo4003.spamdul.parking2.infrastructure.populators;

import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionAmountRepository;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionType;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.reader.JsonReader;
import java.util.List;

public class InfractionAmountPopulatorJson implements InfractionAmountPopulator {

  private final JsonReader jsonReader;
  private final String path;

  public InfractionAmountPopulatorJson(JsonReader jsonReader, String path) {
    this.jsonReader = jsonReader;
    this.path = path;
  }

  @Override
  public void populate(InfractionAmountRepository infractionAmountRepository) {
    List<InfractionDtoJson> infractionDtos = jsonReader.read(path, InfractionDtoJson[].class);
    infractionDtos.forEach(infractionDto -> {
      InfractionType infractionType = InfractionType.valueOf(infractionDto.code);
      Amount amount = Amount.valueOf(infractionDto.montant);
      infractionAmountRepository.save(infractionType, amount);
    });
  }
}
