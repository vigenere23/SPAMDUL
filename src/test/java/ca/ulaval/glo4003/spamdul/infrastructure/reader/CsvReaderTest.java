package ca.ulaval.glo4003.spamdul.infrastructure.reader;

import com.google.common.truth.Truth;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class CsvReaderTest {

  private CsvReader reader;
  private String PATH = "src/test/ressources/test_file.csv";

  @Before
  public void setUp() throws Exception {
    reader = new CsvReader();
  }

  @Test
  public void whenReading_ShouldReturnTheContentOfFile() {
    List<List<String>> fileContent = reader.read(PATH);

    Truth.assertThat(fileContent.get(0)).contains("a");
    Truth.assertThat(fileContent.get(0)).contains("b");
    Truth.assertThat(fileContent.get(0)).contains("c");

    Truth.assertThat(fileContent.get(1)).contains("d");
    Truth.assertThat(fileContent.get(1)).contains("e");
    Truth.assertThat(fileContent.get(1)).contains("f");
  }

  @Test(expected = CantFindFileException.class)
  public void givenAnInvalidPath_whenReading_shouldThrowCantFindFileException() {
    reader.read("invalid/path");
  }
}