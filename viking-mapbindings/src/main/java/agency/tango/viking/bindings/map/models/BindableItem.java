package agency.tango.viking.bindings.map.models;

import agency.tango.viking.bindings.map.listeners.IValueChangedListener;

public class BindableItem<T> {
  private IValueChangedListener<T> listener;
  private T value;

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    if (this.value != value) {
      this.value = value;
      onValueChanged(value);
    }
  }

  public void setOnChangeListener(IValueChangedListener<T> listener) {
    this.listener = listener;
  }

  private void onValueChanged(T value) {
    if (listener != null) {
      listener.onValueChange(value);
    }
  }
}
