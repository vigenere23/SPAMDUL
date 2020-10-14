package ca.ulaval.glo4003.spamdul.infrastructure.db.fundraising;

import ca.ulaval.glo4003.spamdul.entity.fundraising.Initiative;
import ca.ulaval.glo4003.spamdul.entity.fundraising.InitiativeRepository;
import java.util.ArrayList;
import java.util.List;
import jersey.repackaged.com.google.common.collect.Lists;

public class InitiativeRepositoryInMemory implements InitiativeRepository {

  private final List<Initiative> initiatives = new ArrayList<>();

  @Override public List<Initiative> findAll() {
    return Lists.newArrayList(initiatives);
  }

  @Override
  public void save(Initiative initiative) {
    initiatives.add(initiative);
  }
}
