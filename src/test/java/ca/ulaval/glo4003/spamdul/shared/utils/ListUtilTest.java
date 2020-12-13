package ca.ulaval.glo4003.spamdul.shared.utils;

import static com.google.common.truth.Truth.assertThat;

import java.util.List;
import org.junit.Test;

public class ListUtilTest {

  private static class Item {

  }

  @Test
  public void whenConvertingSingleItemToList_shouldReturnSingleItemInList() {
    Item item = new Item();
    List<Item> items = ListUtil.toList(item);

    assertThat(items).containsExactly(item);
  }

  @Test
  public void whenConvertingMultipleItemsToList_shouldReturnMultipleItemsInList() {
    Item item1 = new Item();
    Item item2 = new Item();
    List<Item> items = ListUtil.toList(item1, item2);

    assertThat(items).containsExactly(item1, item2);
  }
}
