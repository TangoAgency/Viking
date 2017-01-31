package agency.tango.viking.bindings.map;

import java.util.List;

public class CollectionUtils {
  private CollectionUtils() {
  }

  public static <T> void moveRange(List<T> list, int fromPosition, int toPosition, int itemCount) {
    for (int i = 0; i < itemCount; i++) {
      move(list, fromPosition, toPosition + itemCount - 1);
    }
  }

  private static <T> void move(List<T> list, int fromPosition, int toPosition) {
    T fromValue = list.get(fromPosition);
    int delta = fromPosition < toPosition ? 1 : -1;
    for (int i = fromPosition; i != toPosition; i += delta) {
      list.set(i, list.get(i + delta));
    }
    list.set(toPosition, fromValue);
  }
}
