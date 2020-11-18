package ca.ulaval.glo4003.spamdul.entity.rechargul;

public interface RechargULCardRepository {

  RechargULCard find(RechargULCardId id);

  void save(RechargULCard rechargULCard);

  void update(RechargULCard rechargULCard);
}
