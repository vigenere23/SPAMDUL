package ca.ulaval.glo4003.spamdul.entity.fundraising;

import java.util.List;

public interface InitiativeRepository {

  List<Initiative> findAll();

  void save(Initiative initiative);
}
