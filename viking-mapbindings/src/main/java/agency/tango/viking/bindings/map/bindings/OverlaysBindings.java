package agency.tango.viking.bindings.map.bindings;

import android.databinding.BindingAdapter;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.GroundOverlayOptions;

import java.util.Collection;

import agency.tango.viking.bindings.map.GoogleMapView;

public class OverlaysBindings {
  private OverlaysBindings() {
  }

  @BindingAdapter("groundOverlays")
  public static void setGroundOverlays(GoogleMapView googleMapView,
      Collection<GroundOverlayOptions> groundOverlay) {
    if (groundOverlay == null) {
      return;
    }

    googleMapView.groundOverlays(groundOverlay);
  }

  @BindingAdapter("groundOverlayClickListener")
  public static void onGroundOverlayClickListener(GoogleMapView googleMapView,
      GoogleMap.OnGroundOverlayClickListener onGroundOverlayClickListener) {
    googleMapView.setOnGroundOverlayClickListener(onGroundOverlayClickListener);
  }
}