package ca.ulaval.glo4003.spamdul.parking2.entities.access.right;

import ca.ulaval.glo4003.spamdul.shared.utils.ListUtil;

public class AccessRightValidatorFactory {

  public AccessRightValidator create(AccessRightFilter... filters) {
    return new AccessRightValidator(ListUtil.toList(filters));
  }
}
