package ca.ulaval.glo4003.spamdul.entity.initiatives;

import java.util.List;

public interface InitiativeRepository {

  List<Initiative> findAll();

  void save(Initiative initiative);
}
