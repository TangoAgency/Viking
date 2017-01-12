package agency.tango.viking.bindings.map;

import android.content.Context;
import android.support.annotation.DrawableRes;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.ClusterRenderer;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

public class ClusterIconRenderer<T extends ClusterItem> extends DefaultClusterRenderer<T> implements ClusterRenderer<T>
{
    private final int markerIcon;

    public ClusterIconRenderer(Context context, GoogleMap map, ClusterManager<T> clusterManager, @DrawableRes int markerIcon)
    {
        super(context, map, clusterManager);
        this.markerIcon = markerIcon;
    }

    @Override
    protected void onBeforeClusterItemRendered(T item, MarkerOptions markerOptions)
    {
        if (markerIcon != 0)
        {
            markerOptions.icon(BitmapDescriptorFactory.fromResource(markerIcon));
        }
        super.onBeforeClusterItemRendered(item, markerOptions);
    }
}