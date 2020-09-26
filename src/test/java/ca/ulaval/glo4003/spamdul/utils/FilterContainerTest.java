package ca.ulaval.glo4003.spamdul.utils;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.google.common.truth.Truth.assertThat;

public class FilterContainerTest {
    static class FilterContainerItem {}

    private FilterContainer<FilterContainerItem> filterContainer;
    private final FilterContainerItem AN_ITEM = new FilterContainerItem();
    private final FilterContainerItem ANOTHER_ITEM = new FilterContainerItem();

    @Before
    public void setUp() {
        filterContainer = new FilterContainer<>();
    }

    @Test
    public void givenNoDataHasBeenSet_whenGettingResults_shouldReturnEmptyList() {
        List<FilterContainerItem> filteredLogs = filterContainer.getResults();
        assertThat(filteredLogs).isEmpty();
    }

    @Test
    public void givenDataIsEmptyList_whenGettingResults_shouldReturnEmptyList() {
        filterContainer.setData(new ArrayList<>());
        List<FilterContainerItem> filteredLogs = filterContainer.getResults();
        assertThat(filteredLogs).isEmpty();
    }

    @Test
    public void givenDataIsEmptyStream_whenGettingResults_shouldReturnEmptyList() {
        filterContainer.setData(Stream.empty());
        List<FilterContainerItem> filteredLogs = filterContainer.getResults();
        assertThat(filteredLogs).isEmpty();
    }

    @Test
    public void givenDataIsAList_whenGettingResults_shouldReturnThatList() {
        List<FilterContainerItem> logs = Arrays.asList(AN_ITEM, ANOTHER_ITEM);
        filterContainer.setData(logs);

        List<FilterContainerItem> filteredLogs = filterContainer.getResults();

        assertThat(filteredLogs).containsExactlyElementsIn(logs);
    }

    @Test
    public void givenDataIsAStream_whenGettingResults_shouldReturnThatStreamAsList() {
        List<FilterContainerItem> logs = Arrays.asList(AN_ITEM, ANOTHER_ITEM);
        filterContainer.setData(logs.stream());

        List<FilterContainerItem> filteredLogs = filterContainer.getResults();

        assertThat(filteredLogs).containsExactlyElementsIn(logs);
    }

    @Test
    public void givenResultsAlreadyCollected_whenGettingResultsASecondTime_shouldReturnEmptyList() {
        filterContainer.setData(Arrays.asList(AN_ITEM, ANOTHER_ITEM));
        filterContainer.getResults();

        List<FilterContainerItem> filteredLogsSecondTime = filterContainer.getResults();

        assertThat(filteredLogsSecondTime).isEmpty();
    }
}
