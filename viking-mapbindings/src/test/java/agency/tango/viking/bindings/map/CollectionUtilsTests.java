package agency.tango.viking.bindings.map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static agency.tango.viking.bindings.map.CollectionUtils.moveRange;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CollectionUtilsTests {
  private int fromPosition;
  private int toPosition;
  private int itemCount;
  private String expectedList;

  public CollectionUtilsTests(int fromPosition, int toPosition, int itemCount,
      String expectedList) {
    this.fromPosition = fromPosition;
    this.toPosition = toPosition;
    this.itemCount = itemCount;
    this.expectedList = expectedList;
  }

  @Parameterized.Parameters(name = "{index}: test moving from position({0}) to {1}")
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] {
        { 0, 9, 1, "[1, 2, 3, 4, 5, 6, 7, 8, 9, 0]" },
        { 9, 0, 1, "[9, 0, 1, 2, 3, 4, 5, 6, 7, 8]" },
        { 8, 2, 2, "[0, 1, 2, 7, 8, 3, 4, 5, 6, 9]" }
    });
  }

  @Test
  public void arrayList_movingItem() throws Exception {
    ArrayList<Integer> list = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      list.add(i);
    }

    moveRange(list, fromPosition, toPosition, itemCount);

    assertEquals(expectedList, list.toString());
  }
}