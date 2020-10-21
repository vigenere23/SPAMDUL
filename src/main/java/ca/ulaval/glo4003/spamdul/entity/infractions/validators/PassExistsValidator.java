package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.pass.exceptions.PassNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;

public class PassExistsValidator extends PassValidator {
    public PassExistsValidator(PassRepository passRepository) {
        super(passRepository);
    }

    @Override
    public void validate(PassToValidateDto passToValidateDto) {
        try {
            getCorrespondingPass(PassCode.valueOf(passToValidateDto.passCode));
        } catch (PassNotFoundException e) {
            throw new InfractionException("VIG_02");
        }

        super.validate(passToValidateDto);
    }
}
