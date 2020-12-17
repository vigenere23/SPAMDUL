package ca.ulaval.glo4003.spamdul.parking.entities.infractions;

import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

public class InfractionFactory {

  private final InfractionIdFactory infractionIdFactory;

  public InfractionFactory(InfractionIdFactory infractionIdFactory) {
    this.infractionIdFactory = infractionIdFactory;
  }

  public Infraction create(InfractionInfosDto infractionInfosDto) {
    InfractionId infractionId = infractionIdFactory.create();
    InfractionCode infractionCode = InfractionCode.valueOf(infractionInfosDto.code);

    return new Infraction(infractionId,
                          infractionInfosDto.infraction,
                          infractionCode,
                          Amount.valueOf(infractionInfosDto.montant));
  }
}
