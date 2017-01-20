package agency.tango.viking.bindings.map.bindings;

import android.databinding.BindingAdapter;

import com.google.android.gms.maps.GoogleMap;

import agency.tango.viking.bindings.map.GoogleMapView;

public class CameraBindings {
  private CameraBindings() {
  }

  @BindingAdapter("gmv_cameraMoveStartedListener")
  public static void cameraMoveStartedListener(GoogleMapView googleMapView,
      GoogleMap.OnCameraMoveStartedListener cameraMoveStartedListener) {
    googleMapView.setOnCameraMoveStartedListener(cameraMoveStartedListener);
  }

  @BindingAdapter("gmv_cameraMoveCanceledListener")
  public static void cameraMoveCanceledListener(GoogleMapView googleMapView,
      GoogleMap.OnCameraMoveCanceledListener cameraMoveCanceledListener) {
    googleMapView.setOnCameraMoveCanceledListener(cameraMoveCanceledListener);
  }

  @BindingAdapter("gmv_cameraIdleListener")
  public static void cameraIdleListener(GoogleMapView googleMapView,
      GoogleMap.OnCameraIdleListener cameraIdleListener) {
    googleMapView.setOnCameraIdleListener(cameraIdleListener);
  }

  @BindingAdapter("gmv_cameraMoveListener")
  public static void onCameraMoveListener(GoogleMapView googleMapView,
      GoogleMap.OnCameraMoveListener onCameraMoveListener) {
    googleMapView.setOnCameraMoveListener(onCameraMoveListener);
  }
}