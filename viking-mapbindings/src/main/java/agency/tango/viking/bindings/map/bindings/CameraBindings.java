package agency.tango.viking.bindings.map.bindings;

import android.databinding.BindingAdapter;

import com.google.android.gms.maps.GoogleMap;

import agency.tango.viking.bindings.map.GoogleMapView;

public class CameraBindings {
  private CameraBindings() {
  }

  @BindingAdapter("cameraMoveStartedListener")
  public static void cameraMoveStartedListener(GoogleMapView googleMapView,
      GoogleMap.OnCameraMoveStartedListener cameraMoveStartedListener) {
    googleMapView.setOnCameraMoveStartedListener(cameraMoveStartedListener);
  }

  @BindingAdapter("cameraMoveCanceledListener")
  public static void cameraMoveCanceledListener(GoogleMapView googleMapView,
      GoogleMap.OnCameraMoveCanceledListener cameraMoveCanceledListener) {
    googleMapView.setOnCameraMoveCanceledListener(cameraMoveCanceledListener);
  }

  @BindingAdapter("cameraIdleListener")
  public static void cameraIdleListener(GoogleMapView googleMapView,
      GoogleMap.OnCameraIdleListener cameraIdleListener) {
    googleMapView.setOnCameraIdleListener(cameraIdleListener);
  }

  @BindingAdapter("cameraMoveListener")
  public static void onCameraMoveListener(GoogleMapView googleMapView,
      GoogleMap.OnCameraMoveListener onCameraMoveListener) {
    googleMapView.setOnCameraMoveListener(onCameraMoveListener);
  }
}