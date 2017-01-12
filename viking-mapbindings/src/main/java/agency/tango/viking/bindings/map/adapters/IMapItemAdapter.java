package agency.tango.viking.bindings.map.adapters;

import android.support.annotation.DrawableRes;
import com.google.android.gms.maps.model.LatLng;

public interface IMapItemAdapter<T>
{
    @DrawableRes
    int icon(T item);

    LatLng position(T item);
}
