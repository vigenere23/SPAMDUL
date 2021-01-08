package ca.ulaval.glo4003.spamdul.shared.infrastructure.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvReader {

  public List<List<String>> read(String filePath) {
    List<List<String>> csvData = new ArrayList<>();

    try (BufferedReader csvReader = new BufferedReader(new FileReader(filePath))) {
      String row;

      while ((row = csvReader.readLine()) != null) {
        String[] data = row.split(",");
        csvData.add(new ArrayList<>(Arrays.asList(data)));
      }

    } catch (IOException e) {
      throw new InvalidCsvFile(filePath);
    }

    return csvData;
  }

  public Map<String, Map<String, String>> readTable(String filePath) {
    Map<String, Map<String, String>> data = new HashMap();
    boolean isFirstRow = true;

    try (BufferedReader csvReader = new BufferedReader(new FileReader(filePath))) {
      String row;
      List<String> columnIds = new ArrayList<>();

      while ((row = csvReader.readLine()) != null) {
        List<String> rowItems = new ArrayList<>(Arrays.asList(row.split(",")));

        if (isFirstRow) {
          rowItems.remove(0);
          columnIds = rowItems;
          isFirstRow = false;
        } else {
          String rowId = rowItems.remove(0);
          for (int i = 0; i < columnIds.size(); i++) {
            Map<String, String> dataRow = data.getOrDefault(rowId, new HashMap<>());
            dataRow.put(columnIds.get(i), rowItems.get(i));
            data.put(rowId, dataRow);
          }
        }
      }

    } catch (IOException | IndexOutOfBoundsException e) {
      throw new InvalidCsvFile(filePath);
    }

    return data;
  }
}
