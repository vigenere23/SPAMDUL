package ca.ulaval.glo4003.spamdul.shared.api;

import ca.ulaval.glo4003.spamdul.shared.api.exceptions.NotFoundException;
import ca.ulaval.glo4003.spamdul.shared.api.exceptions.ServerErrorException;
import ca.ulaval.glo4003.spamdul.shared.api.exceptions.UserErrorException;
import ca.ulaval.glo4003.spamdul.shared.usecases.exceptions.InvalidArgumentException;
import ca.ulaval.glo4003.spamdul.shared.usecases.exceptions.ItemNotFoundException;
import ca.ulaval.glo4003.spamdul.shared.usecases.exceptions.UnauthorizedException;
import ca.ulaval.glo4003.spamdul.shared.usecases.exceptions.UnhandledException;
import java.util.concurrent.Callable;

public class ApiExceptionWrapper {

  public static <T> T wrap(Callable<T> function) {
    try {
      return function.call();
    } catch (ItemNotFoundException exception) {
      throw new NotFoundException(exception);
    } catch (IllegalArgumentException | InvalidArgumentException exception) {
      throw new UserErrorException(exception);
    } catch (UnauthorizedException exception) {
      throw new ca.ulaval.glo4003.spamdul.shared.api.exceptions.UnauthorizedException(exception);
    } catch (UnhandledException exception) {
      throw new ServerErrorException(exception);
    } catch (Exception exception) {
      throw new ServerErrorException(exception);
    }
  }

  public static void wrap(Runnable function) {
    Callable<Void> callable = () -> {
      function.run();
      return null;
    };

    wrap(callable);
  }
}
