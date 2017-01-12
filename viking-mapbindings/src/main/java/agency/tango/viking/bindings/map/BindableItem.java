package agency.tango.viking.bindings.map;

import agency.tango.viking.bindings.map.listeners.IValueChangedListener;

public class BindableItem<T>
{
    private IValueChangedListener<T> listener;
    private T value;

    public T getValue()
    {
        return value;
    }

    public void setValue(T value)
    {
        this.value = value;
    }

    public void setOnChangeListener(IValueChangedListener<T> listener)
    {
        this.listener = listener;
    }

    public void onValueChanged(T value)
    {
        if (listener != null)
        {
            listener.onValueChange(value);
        }
    }
}
