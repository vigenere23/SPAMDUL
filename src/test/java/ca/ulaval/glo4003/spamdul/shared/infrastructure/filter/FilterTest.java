package ca.ulaval.glo4003.spamdul.shared.infrastructure.filter;

import static com.google.common.truth.Truth.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FilterTest {

  static class FilterContainerItem {

  }

  private Filter<FilterContainerItem> filter;
  private final FilterContainerItem AN_ITEM = new FilterContainerItem();
  private final FilterContainerItem ANOTHER_ITEM = new FilterContainerItem();

  @Before
  public void setUp() {
    filter = new Filter<>();
  }

  @Test(expected = FilterDataNotSetException.class)
  public void givenNoDataHasBeenSet_whenGettingResults_shouldThrowException() {
    filter.getResults();
  }

  @Test
  public void givenDataIsEmptyList_whenGettingResults_shouldReturnEmptyList() {
    filter.setData(new ArrayList<>());

    List<FilterContainerItem> filteredItems = filter.getResults();

    assertThat(filteredItems).isEmpty();
  }

  @Test
  public void givenDataIsAList_whenGettingResults_shouldReturnThatList() {
    List<FilterContainerItem> items = Arrays.asList(AN_ITEM, ANOTHER_ITEM);
    filter.setData(items);

    List<FilterContainerItem> filteredItems = filter.getResults();

    assertThat(filteredItems).containsExactlyElementsIn(items);
  }

  @Test
  public void givenResultsAlreadyCollected_whenGettingResultsASecondTime_shouldReturnSameResults() {
    filter.setData(Arrays.asList(AN_ITEM, ANOTHER_ITEM));
    List<FilterContainerItem> filteredItemsFirstTime = filter.getResults();

    List<FilterContainerItem> filteredItemsSecondTime = filter.getResults();

    assertThat(filteredItemsSecondTime).containsExactlyElementsIn(filteredItemsFirstTime);
  }
}
