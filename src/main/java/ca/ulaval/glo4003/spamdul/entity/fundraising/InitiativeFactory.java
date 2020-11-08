package ca.ulaval.glo4003.spamdul.entity.fundraising;

import ca.ulaval.glo4003.spamdul.entity.fundraising.exceptions.InvalidInitiativeAmount;
import ca.ulaval.glo4003.spamdul.entity.fundraising.exceptions.InvalidInitiativeName;
import ca.ulaval.glo4003.spamdul.entity.idgenerator.IdGenerator;
import ca.ulaval.glo4003.spamdul.entity.idgenerator.IncrementalLongIdGenerator;
import ca.ulaval.glo4003.spamdul.utils.Amount;

public class InitiativeFactory {

  private static final IdGenerator<Long> codeGenerator = new IncrementalLongIdGenerator();

  public Initiative create(String name, Amount amount) {
    String code = codeGenerator.getNextId().toString();
    return create(code, name, amount);
  }

  public Initiative create(String code, String name, Amount amount) {
    if (name == null || name.isEmpty()) {
      throw new InvalidInitiativeName("A name must be provided");
    }

    if (amount.isZero() || amount.isNegative()) {
      throw new InvalidInitiativeAmount("Amount must be greather than zero");
    }

    if (code == null) {
      code = codeGenerator.getNextId().toString();
    }

    return new Initiative(new InitiativeId(), code, name, amount);
  }
}
