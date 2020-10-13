package ca.ulaval.glo4003.spamdul.entity.pass.exceptions;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidPassSaleArgumentException;

public class PassSaleNotAcceptedByAccessException extends InvalidPassSaleArgumentException {
    public PassSaleNotAcceptedByAccessException(String message) {
        super(message);
    }
}
