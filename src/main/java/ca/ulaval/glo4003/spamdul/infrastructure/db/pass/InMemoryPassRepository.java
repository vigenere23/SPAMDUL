package ca.ulaval.glo4003.spamdul.infrastructure.db.pass;

import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.pass.exceptions.PassNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class InMemoryPassRepository implements PassRepository {

  private Map<PassCode, Pass> passes = new HashMap<>();
  private static final Logger logger = Logger.getLogger(InMemoryPassRepository.class.getName());

  public void save(Pass pass) {
    passes.put(pass.getPassCode(), pass);
    String loggingInfos = String.format("Saving pass: %s", pass.getPassCode().toString());
    logger.info(loggingInfos);
  }

  public Pass findByPassCode(PassCode passCode) {
    Pass pass = passes.get(passCode);

    if (pass == null) {
      throw new PassNotFoundException(String.format("No pass with id %s", passCode.toString()));
    }

    return pass;
  }
}
