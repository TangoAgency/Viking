package agency.tango.viking.bindings.map;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.location.Location;

import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.heatmaps.Gradient;

import java.util.Collection;

import agency.tango.viking.bindings.map.adapters.IClusterItemAdapter;
import agency.tango.viking.bindings.map.adapters.ItemPopupAdapter;
import agency.tango.viking.bindings.map.clickHandlers.ClusterClickHandler;
import agency.tango.viking.bindings.map.clickHandlers.ItemClickHandler;

@SuppressWarnings({ "unchecked", "unused" })
public class MapBindings {
  private MapBindings() {
  }

  @BindingAdapter("radius")
  public static void setRadius(GoogleMapView bindableMap, int radius) {
    bindableMap.radius().setValue(radius);
  }

  @InverseBindingAdapter(attribute = "radius", event = "radiusChanged")
  public static int getRadius(GoogleMapView bindableMap) {
    return (int) bindableMap.radius().getValue();
  }

  @BindingAdapter("radiusChanged")
  public static void setRadiusOnChangedListener(GoogleMapView bindableMap,
      InverseBindingListener bindingListener) {
    setOnValueChangedListener(bindableMap.radius(), bindingListener);
  }

  @BindingAdapter("zoom")
  public static void setZoom(GoogleMapView bindableMap, float zoom) {
    bindableMap.zoom().setValue(zoom);
  }

  @InverseBindingAdapter(attribute = "zoom", event = "zoomChanged")
  public static float getZoom(GoogleMapView bindableMap) {
    return (float) bindableMap.zoom().getValue();
  }

  @BindingAdapter("zoomChanged")
  public static void setZoomOnChangedListener(GoogleMapView bindableMap,
      InverseBindingListener bindingListener) {
    setOnValueChangedListener(bindableMap.zoom(), bindingListener);
  }

  @BindingAdapter("location")
  public static void setLocation(GoogleMapView bindableMap, Location location) {
    if (location != bindableMap.location().getValue()) {
      bindableMap.postChangedLocation(location);
    }
  }

  @InverseBindingAdapter(attribute = "location", event = "locationChanged")
  public static Location getLocation(GoogleMapView bindableMap) {
    return (Location) bindableMap.location().getValue();
  }

  @BindingAdapter("locationChanged")
  public static void setLocationChangedListener(GoogleMapView bindableMap,
      InverseBindingListener bindingListener) {
    setOnValueChangedListener(bindableMap.location(), bindingListener);
  }

  @BindingAdapter("markerClick")
  public static void markerClick(GoogleMapView customMarkerMap, ItemClickHandler clickHandler) {
    customMarkerMap.markerClicked(clickHandler);
  }

  @BindingAdapter("markerPopupAdapter")
  public static void markerPopupAdapter(GoogleMapView customMarkerMap,
      ItemPopupAdapter infoWindowAdapter) {
    customMarkerMap.popupInfoAdapter(infoWindowAdapter);
  }

  @BindingAdapter("markerPopupClick")
  public static void markerPopupClick(GoogleMapView customMarkerMap,
      ItemClickHandler clickHandler) {
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
  public static void mapClusteringAdapter(GoogleMapView customMarkerMap,
      IClusterItemAdapter adapter) {
    //        customMarkerMap.clusterAdapter(adapter);
  }

  @BindingAdapter("mapItems")
  public static <T> void mapItems(GoogleMapView customMarkerMap, Collection<T> items) {
    if (items == null) {
      return;
    }
    customMarkerMap.markers(items);
  }

  @BindingAdapter("clusterClick")
  public static void clusterClick(GoogleMapView clusteredMap,
      ClusterClickHandler clusterClickHandler) {
    //        clusteredMap.clusterClicked(clusterClickHandler);
  }

  @BindingAdapter("heatMapRadius")
  public static void heatRadius(GoogleMapView heatMap, int heatRadius) {
    //        heatMap.heatMapRadius(heatRadius);
  }

  @BindingAdapter("heatMapGradient")
  public static void gradient(GoogleMapView heatMap, Gradient gradient) {
    //        heatMap.heatMapGradient(gradient);
  }

  @BindingAdapter("heatMapOpacity")
  public static void opacity(GoogleMapView heatMap, double opacity) {
    //        heatMap.heatMapOpacity(opacity);
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
