package ca.ulaval.glo4003.spamdul.shared.utils;

import java.net.URI;

public class UriUtil {

  public static URI concatenate(String... paths) {
    if (paths == null || paths.length == 0) {
      return URI.create("");
    }

    StringBuilder pathBuilder = new StringBuilder();

    for (String path : paths) {
      pathBuilder.append(path);

      if (path.charAt(path.length() - 1) != '/') {
        pathBuilder.append('/');
      }
    }

    return URI.create(pathBuilder.toString());
  }
}
