package ca.ulaval.glo4003.spamdul.entity.parking.pass;

public interface PassRepository {

  Pass findByPassCode(PassCode passCode);
}
