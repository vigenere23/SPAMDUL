package ca.ulaval.glo4003.spamdul.entity.pass;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidPassArgumentException;

public class PassNotAcceptedByAccessException extends InvalidPassArgumentException {
    public PassNotAcceptedByAccessException(String message) {
        super(message);
    }
}
