package ca.ulaval.glo4003.spamdul.infrastructure.filter;

import static com.google.common.truth.Truth.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

  @Test(expected = FilterDataNotSetException.class)
  public void givenNoDataHasBeenSet_whenGettingResults_shouldThrowException() {
    filterContainer.getResults();
  }

  @Test
  public void givenDataIsEmptyList_whenGettingResults_shouldReturnEmptyList() {
    filterContainer.setData(new ArrayList<>());
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
  public void givenResultsAlreadyCollected_whenGettingResultsASecondTime_shouldReturnSameResults() {
    filterContainer.setData(Arrays.asList(AN_ITEM, ANOTHER_ITEM));
    List<FilterContainerItem> filteredItemsFirstTime = filterContainer.getResults();

    List<FilterContainerItem> filteredItemsSecondTime = filterContainer.getResults();

    assertThat(filteredItemsSecondTime).containsExactlyElementsIn(filteredItemsFirstTime);
  }
}
