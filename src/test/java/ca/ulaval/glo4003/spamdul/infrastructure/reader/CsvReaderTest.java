package ca.ulaval.glo4003.spamdul.infrastructure.reader;

import static com.google.common.truth.Truth.assertThat;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class CsvReaderTest {

  private CsvReader reader;
  private final String PATH = "src/test/ressources/test_file.csv";

  @Before
  public void setUp() throws Exception {
    reader = new CsvReader();
  }

  @Test
  public void whenReading_ShouldReturnTheContentOfFile() {
    List<List<String>> fileContent = reader.read(PATH);

    assertThat(fileContent.get(0)).contains("a");
    assertThat(fileContent.get(0)).contains("b");
    assertThat(fileContent.get(0)).contains("c");

    assertThat(fileContent.get(1)).contains("d");
    assertThat(fileContent.get(1)).contains("e");
    assertThat(fileContent.get(1)).contains("f");
  }

  @Test(expected = InvalidCsvFile.class)
  public void givenAnInvalidPath_whenReading_shouldThrowCantFindFileException() {
    reader.read("invalid/path");
  }
}