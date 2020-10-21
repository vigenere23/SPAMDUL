package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;

public class EmptyPassCodeValidator extends PassValidator {
    public EmptyPassCodeValidator(PassRepository passRepository) {
        super(passRepository);
    }

    @Override
    public void validate(PassToValidateDto passToValidateDto) {
        if (passToValidateDto.passCode.equals("")) {
            throw new InfractionException("VIG_03");
        }

        super.validate(passToValidateDto);
    }
}
