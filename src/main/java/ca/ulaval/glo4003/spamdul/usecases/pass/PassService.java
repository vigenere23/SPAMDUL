package ca.ulaval.glo4003.spamdul.usecases.pass;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassFactory;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
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

    public void createPass(UserId userId, ParkingZone parkingZone, int numberOfTerms) {
        userRepository.findById(userId);
        Pass pass = passFactory.create(userId, parkingZone, numberOfTerms);
        passRepository.save(pass);
    }
}
