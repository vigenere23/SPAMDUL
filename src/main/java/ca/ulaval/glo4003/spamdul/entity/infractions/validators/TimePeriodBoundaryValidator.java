package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;

public class TimePeriodBoundaryValidator extends PassValidator {
    private final Calendar calendar;

    public TimePeriodBoundaryValidator(PassRepository passRepository, Calendar calendar) {
        super(passRepository);
        this.calendar = calendar;
    }


    @Override
    public void validate(PassToValidateDto passToValidateDto) {
        Pass pass = getCorrespondingPass(PassCode.valueOf(passToValidateDto.passCode));

        if (!pass.getTimePeriod().bounds(calendar.now())) {

            throw new InfractionException("VIG_02");
        }

        super.validate(passToValidateDto);
    }
}
