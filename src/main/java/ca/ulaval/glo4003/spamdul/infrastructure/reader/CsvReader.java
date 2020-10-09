package ca.ulaval.glo4003.spamdul.infrastructure.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvReader {

  public List<List<String>> read(String filePath) {
    List<List<String>> csvData = new ArrayList<>();

    try {
      try (BufferedReader csvReader = new BufferedReader(new FileReader(filePath))) {
        String row;
        while ((row = csvReader.readLine()) != null) {
          String[] data = row.split(",");
          csvData.add(new ArrayList<>(Arrays.asList(data)));
        }
      }
    } catch (IOException e) {
      //euhhhh
    }

    return csvData;
  }
}
