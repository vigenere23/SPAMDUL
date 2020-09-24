package ca.ulaval.glo4003.spamdul.entity.pass;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions.InvalidUserArgumentException;

public class InvalidNumberOfTermsException extends InvalidUserArgumentException {

    public InvalidNumberOfTermsException(String message) {
        super(message);
    }
}
