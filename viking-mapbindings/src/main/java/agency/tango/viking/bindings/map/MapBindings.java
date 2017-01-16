package agency.tango.viking.bindings.map;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.util.Collection;

import agency.tango.viking.bindings.map.adapters.IClusterItemAdapter;
import agency.tango.viking.bindings.map.clickHandlers.ClusterClickHandler;
import agency.tango.viking.bindings.map.clickHandlers.ItemClickListener;

@SuppressWarnings({ "unchecked", "unused" })
public class MapBindings {
  private MapBindings() {
  }

  @BindingAdapter("radius")
  public static void setRadius(GoogleMapView googleMapView, int radius) {
    googleMapView.radius().setValue(radius);
  }

  @InverseBindingAdapter(attribute = "radius", event = "radiusChanged")
  public static int getRadius(GoogleMapView googleMapView) {
    return (int) googleMapView.radius().getValue();
  }

  @BindingAdapter("radiusChanged")
  public static void setRadiusOnChangedListener(GoogleMapView googleMapView,
      InverseBindingListener bindingListener) {
    setOnValueChangedListener(googleMapView.radius(), bindingListener);
  }

  @BindingAdapter("zoom")
  public static void setZoom(GoogleMapView googleMapView, float zoom) {
    googleMapView.zoom().setValue(zoom);
  }

  @InverseBindingAdapter(attribute = "zoom", event = "zoomChanged")
  public static float getZoom(GoogleMapView googleMapView) {
    return (float) googleMapView.zoom().getValue();
  }

  @BindingAdapter("zoomChanged")
  public static void setZoomOnChangedListener(GoogleMapView googleMapView,
      InverseBindingListener bindingListener) {
    setOnValueChangedListener(googleMapView.zoom(), bindingListener);
  }

  @BindingAdapter("location")
  public static void setLocation(GoogleMapView googleMapView, Location location) {
    if (location != googleMapView.location().getValue()) {
      googleMapView.postChangedLocation(location);
    }
  }

  @InverseBindingAdapter(attribute = "location", event = "locationChanged")
  public static Location getLocation(GoogleMapView googleMapView) {
    return (Location) googleMapView.location().getValue();
  }

  @BindingAdapter("locationChanged")
  public static void setLocationChangedListener(GoogleMapView googleMapView,
      InverseBindingListener bindingListener) {
    setOnValueChangedListener(googleMapView.location(), bindingListener);
  }

  @BindingAdapter("markerClick")
  public static void markerClick(GoogleMapView googleMapView, ItemClickListener itemClickListener) {
    googleMapView.markerClick(itemClickListener);
  }

  @BindingAdapter("infoWindowAdapter")
  public static void infoWindowAdapter(GoogleMapView googleMapView,
      GoogleMap.InfoWindowAdapter infoWindowAdapter) {
    googleMapView.infoWindowAdapter(infoWindowAdapter);
  }

  @BindingAdapter("infoWindowClick")
  public static void infoWindowClick(GoogleMapView googleMapView,
      ItemClickListener itemClickListener) {
    googleMapView.infoWindowClick(itemClickListener);
  }

  //
  //@BindingAdapter("markerPopupAdapter")
  //public static void markerPopupAdapter(GoogleMapView customMarkerMap,
  //    ItemPopupAdapter infoWindowAdapter) {
  //  customMarkerMap.popupInfoAdapter(infoWindowAdapter);
  //}

  @BindingAdapter("markerPopupClick")
  public static void markerPopupClick(GoogleMapView customMarkerMap,
      ItemClickListener clickHandler) {
    //        customMarkerMap.markerPopupClicked(clickHandler);
  }

  @BindingAdapter("paths")
  public static void setPaths(GoogleMapView customMarkerMap,
      Collection<PolylineOptions> polylines) {
    if (polylines == null) {
      return;
    }
    customMarkerMap.paths(polylines);
  }

  @BindingAdapter("overlays")
  public static void setOverlays(GoogleMapView googleMapView,
      Collection<GroundOverlayOptions> overlays) {
    if (overlays == null) {
      return;
    }

    googleMapView.overlays(overlays);
  }

  @BindingAdapter("mapClusteringAdapter")
  public static void mapClusteringAdapter(GoogleMapView googleMapView,
      IClusterItemAdapter adapter) {
    //        customMarkerMap.clusterAdapter(adapter);
  }

  @BindingAdapter("mapItems")
  public static <T> void mapItems(GoogleMapView googleMapView, Collection<T> items) {
    if (items == null) {
      return;
    }
    googleMapView.markers(items);
  }

  @BindingAdapter("clusterClick")
  public static void clusterClick(GoogleMapView googleMapView,
      ClusterClickHandler clusterClickHandler) {
    //        clusteredMap.clusterClicked(clusterClickHandler);
  }

  @BindingAdapter("heatMap")
  public static void heatRadius(GoogleMapView googleMapView,
      HeatmapTileProvider heatmapTileProvider) {
    googleMapView.heatMap(heatmapTileProvider);
  }

  private static void setOnValueChangedListener(BindableItem item,
      InverseBindingListener bindingListener) {
    if (bindingListener == null) {
      item.setOnChangeListener(null);
    } else {
      item.setOnChangeListener(value -> bindingListener.onChange());
    }
  }
}
