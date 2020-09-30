package ca.ulaval.glo4003.spamdul.utils;

import static com.google.common.truth.Truth.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;

public class FilterContainerTest {

  static class FilterContainerItem {

  }

  private FilterContainer<FilterContainerItem> filterContainer;
  private final FilterContainerItem AN_ITEM = new FilterContainerItem();
  private final FilterContainerItem ANOTHER_ITEM = new FilterContainerItem();

  @Before
  public void setUp() {
    filterContainer = new FilterContainer<>();
  }

  @Test
  public void givenNoDataHasBeenSet_whenGettingResults_shouldReturnEmptyList() {
    List<FilterContainerItem> filteredItems = filterContainer.getResults();
    assertThat(filteredItems).isEmpty();
  }

  @Test
  public void givenDataIsEmptyList_whenGettingResults_shouldReturnEmptyList() {
    filterContainer.setData(new ArrayList<>());
    List<FilterContainerItem> filteredItems = filterContainer.getResults();
    assertThat(filteredItems).isEmpty();
  }

  @Test
  public void givenDataIsEmptyStream_whenGettingResults_shouldReturnEmptyList() {
    filterContainer.setData(Stream.empty());
    List<FilterContainerItem> filteredItems = filterContainer.getResults();
    assertThat(filteredItems).isEmpty();
  }

  @Test
  public void givenDataIsAList_whenGettingResults_shouldReturnThatList() {
    List<FilterContainerItem> items = Arrays.asList(AN_ITEM, ANOTHER_ITEM);
    filterContainer.setData(items);

    List<FilterContainerItem> filteredItems = filterContainer.getResults();

    assertThat(filteredItems).containsExactlyElementsIn(items);
  }

  @Test
  public void givenDataIsAStream_whenGettingResults_shouldReturnThatStreamAsList() {
    List<FilterContainerItem> items = Arrays.asList(AN_ITEM, ANOTHER_ITEM);
    filterContainer.setData(items.stream());

    List<FilterContainerItem> filteredItems = filterContainer.getResults();

    assertThat(filteredItems).containsExactlyElementsIn(items);
  }

  @Test
  public void givenResultsAlreadyCollected_whenGettingResultsASecondTime_shouldReturnEmptyList() {
    filterContainer.setData(Arrays.asList(AN_ITEM, ANOTHER_ITEM));
    filterContainer.getResults();

    List<FilterContainerItem> filteredItemsSecondTime = filterContainer.getResults();

    assertThat(filteredItemsSecondTime).isEmpty();
  }
}
