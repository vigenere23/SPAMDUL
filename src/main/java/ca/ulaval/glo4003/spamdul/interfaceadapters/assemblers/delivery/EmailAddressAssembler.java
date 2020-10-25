package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery;

import ca.ulaval.glo4003.spamdul.entity.delivery.email.EmailAddress;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.exceptions.InvalidEmailAddressException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailAddressAssembler {

  public EmailAddress fromString(String stringAddress) {
    Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(stringAddress);

    if (!matcher.find()) {
      throw new InvalidEmailAddressException("Invalid email");
    }
    return new EmailAddress(stringAddress);
  }
}
