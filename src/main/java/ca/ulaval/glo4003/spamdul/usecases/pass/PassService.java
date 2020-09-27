package ca.ulaval.glo4003.spamdul.usecases.pass;

import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassFactory;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;

public class PassService {
    private PassRepository passRepository;
    private UserRepository userRepository;
    private PassFactory passFactory;

    public PassService(PassRepository passRepository, UserRepository userRepository, PassFactory passFactory) {
        this.passRepository = passRepository;
        this.userRepository = userRepository;
        this.passFactory = passFactory;
    }

    public PassCode createPass(PassDto passDto) {
        userRepository.findById(passDto.userId);
        Pass pass = passFactory.create(passDto.userId, passDto.parkingZone, passDto.passType);
        passRepository.save(pass);

        return pass.getPassCode();
    }
}
