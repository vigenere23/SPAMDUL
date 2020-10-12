package ca.ulaval.glo4003.spamdul.infrastructure.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class JsonReader {

  public <T> List<T> read(String path, Class<T[]> aClass) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return Arrays.asList(mapper.readValue(Paths.get(path).toFile(), aClass));

    } catch (IOException e) {
      throw new CantReadFileFromPathException("the given path does not correspond to any valid json file");
    }
  }
}
