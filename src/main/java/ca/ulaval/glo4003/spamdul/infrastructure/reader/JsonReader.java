package ca.ulaval.glo4003.spamdul.infrastructure.reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
    } catch (JsonMappingException e) {
      throw new InvalidJsonFile(String.format("could not parse JSON to class %s", aClass.getName()));
    } catch (JsonProcessingException e) {
      throw new InvalidJsonFile(String.format("could not process file as valid JSON format at path %s", path));
    } catch (IOException e) {
      throw new InvalidJsonFile(String.format("could not read JSON file at path %s", path));
    }
  }
}
