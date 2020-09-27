package ca.ulaval.glo4003.spamdul.infrastructure.db.pass;

import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import java.util.HashMap;
import java.util.Map;

public class PassRepositoryInMemory implements PassRepository {

  private Map<PassCode, Pass> passes = new HashMap<>();

  public void save(Pass pass) {

    passes.put(pass.getPassCode(), pass);
  }

  public Pass findByPassCode(PassCode passCode) {
    return passes.get(passCode);
  }
}
