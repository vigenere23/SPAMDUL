package ca.ulaval.glo4003.spamdul.entity.infractions.exceptions;

public class PassRepositoryNotSetException extends RuntimeException {
    public PassRepositoryNotSetException(String message) {
        super(message);
    }
}
