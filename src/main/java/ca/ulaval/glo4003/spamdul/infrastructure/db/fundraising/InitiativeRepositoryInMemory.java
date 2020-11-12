package ca.ulaval.glo4003.spamdul.infrastructure.db.fundraising;

import ca.ulaval.glo4003.spamdul.entity.initiatives.Initiative;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import jersey.repackaged.com.google.common.collect.Lists;

public class InitiativeRepositoryInMemory implements InitiativeRepository {

  private final List<Initiative> initiatives = new ArrayList<>();
  private static final Logger logger = Logger.getLogger(InitiativeRepositoryInMemory.class.getName());

  @Override public List<Initiative> findAll() {
    return Lists.newArrayList(initiatives);
  }

  @Override
  public void save(Initiative initiative) {
    initiatives.add(initiative);
    logger.info(String.format("Saved initiative %s : %s", initiative.getId(), initiative.getName()));
  }
}
