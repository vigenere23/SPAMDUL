package ca.ulaval.glo4003.spamdul.entity.infractions;

public class AlreadyPaidInfractionException extends RuntimeException {
    public AlreadyPaidInfractionException(String s) {
        super(s);
    }
}
