package ca.ulaval.glo4003.spamdul.entity.infractions;

public class InfractionFactory {

    public Infraction create(InfractionInfos infractionInfos) {
        InfractionId infractionId = new InfractionId();
        InfractionCode infractionCode = InfractionCode.valueOf(infractionInfos.code);
        return new Infraction(infractionId, infractionInfos.infraction, infractionCode, infractionInfos.montant);
    }
}
