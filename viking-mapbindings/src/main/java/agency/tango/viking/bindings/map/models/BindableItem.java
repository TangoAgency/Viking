package agency.tango.viking.bindings.map.models;

import agency.tango.viking.bindings.map.listeners.IValueChangedListener;

public class BindableItem<T> {

  private final Binding<T> binding;

  private IValueChangedListener<T> listener;
  private T value;
  private boolean enabled;

  public BindableItem(Binding<T> binding) {
    this.binding = binding;
  }

  public BindableItem() {
    this(value -> {
    });
  }

  public void enable() {
    this.enabled = true;
  }

  public void disable() {
    this.enabled = false;
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    if (!equals(this.value, value) && enabled) {
      this.value = value;
      this.binding.set(value);
      onValueChanged(value);
    }
  }

  public void setValueAndDisable(T value) {
    if (!equals(this.value, value)) {
      this.value = value;
      this.binding.set(value);
      onValueChanged(value);
    }
    disable();
  }

  public void setOnChangeListener(IValueChangedListener<T> listener) {
    this.listener = listener;
  }

  private void onValueChanged(T value) {
    if (listener != null) {
      listener.onValueChange(value);
    }
  }

  public interface Binding<T> {
    void set(T value);
  }

  /**
   * Returns {@code true} if the arguments are equal to each other
   * and {@code false} otherwise.
   * Consequently, if both arguments are {@code null}, {@code true}
   * is returned and if exactly one argument is {@code null}, {@code
   * false} is returned.  Otherwise, equality is determined by using
   * the {@link Object#equals equals} method of the first
   * argument.
   *
   * @param a an object
   * @param b an object to be compared with {@code a} for equality
   * @return {@code true} if the arguments are equal to each other
   * and {@code false} otherwise
   * @see Object#equals(Object)
   */
  public static boolean equals(Object a, Object b) {
    return (a == b) || (a != null && a.equals(b));
  }
}
