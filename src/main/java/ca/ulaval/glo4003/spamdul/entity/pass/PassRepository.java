package ca.ulaval.glo4003.spamdul.entity.pass;

public interface PassRepository {

  Pass findByPassCode(PassCode passCode);
}
