package ca.ulaval.glo4003.spamdul.finance.entities.initiatives;

import java.util.List;

public interface InitiativeRepository {

  List<Initiative> findAll();

  void save(Initiative initiative);
}
