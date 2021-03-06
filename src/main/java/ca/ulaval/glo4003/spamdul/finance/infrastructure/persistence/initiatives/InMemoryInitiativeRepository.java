package ca.ulaval.glo4003.spamdul.finance.infrastructure.persistence.initiatives;

import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.Initiative;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.InitiativeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import jersey.repackaged.com.google.common.collect.Lists;

public class InMemoryInitiativeRepository implements InitiativeRepository {

  private final List<Initiative> initiatives = new ArrayList<>();
  private static final Logger logger = Logger.getLogger(InMemoryInitiativeRepository.class.getName());

  @Override public List<Initiative> findAll() {
    return Lists.newArrayList(initiatives);
  }

  @Override
  public void save(Initiative initiative) {
    initiatives.add(initiative);
    logger.info(String.format("Saved initiative %s : %s", initiative.getId(), initiative.getName()));
  }
}
