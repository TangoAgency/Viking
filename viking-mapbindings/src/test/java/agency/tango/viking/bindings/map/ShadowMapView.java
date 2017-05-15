package agency.tango.viking.bindings.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;

import org.robolectric.annotation.Implements;

@Implements(GoogleMap.class)
public class ShadowMapView {

  public void __constructor__(IGoogleMapDelegate var1) {

  }
}