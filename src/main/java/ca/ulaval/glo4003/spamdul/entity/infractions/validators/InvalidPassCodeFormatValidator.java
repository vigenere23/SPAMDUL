package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.pass.exceptions.InvalidPassCodeFormat;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;

public class InvalidPassCodeFormatValidator extends PassValidator {
    public InvalidPassCodeFormatValidator(PassRepository passRepository) {
        super(passRepository);
    }

    @Override
    public void validate(PassToValidateDto passToValidateDto) {
        try {
            PassCode.valueOf(passToValidateDto.passCode);
        } catch (InvalidPassCodeFormat e) {
            throw new InfractionException("VIG_02");
        }

        super.validate(passToValidateDto);
    }
}
