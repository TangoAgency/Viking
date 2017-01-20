# Viking Map Bindings
Viking Map Bindings is a library for using Google MapView with Android Data Binding with many API improvements.

### To use it first please read this: [Android Data Binding]
## Usage
### Step 1:
Enable Data Binding in your project [Android Data Binding Setup]
### Step 2:
Make your Activity extend  [MapAwareActivityView](for Fragment use [MapAwareFragmentView])
```java
public class MapActivity extends MapAwareActivityView<ViewModel, Binding>
```
### Step 3:
Add to your [xml] [GoogleMapView]
```
 <agency.tango.viking.bindings.map.GoogleMapView
      android:id="@+id/map"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      />
```
### Step 4:
Bind EVERYTHING!!!

Example based on [Viking ViewModel]:
  - [MapViewModel]
  - [MapActivity]
  - [activity_map.xml]

## Available Bindings
- [Camera Bindings]:
```
  - gmv_cameraMoveStartedListener
  - gmv_cameraMoveCanceledListener
  - gmv_cameraIdleListener
  - gmv_cameraMoveListener
```
- [Circle Bindings]:
```
  - gmv_circles
  - gmv_circleClickListener
```
- [Cluster Bindings]:
```
  - gmv_clusterItems
  - gmv_rendererFactory
  - gmv_algorithm
  - gmv_clusterItemInfoWindowAdapter
  - gmv_clusterInfoWindowAdapter
  - gmv_clusterClickListener
  - gmv_clusterItemClickListener
  - gmv_clusterInfoWindowClickListener
  - gmv_onClusterItemInfoWindowClickListener
```
- [Map Bindings]:
```
  - gmv_radius
  - gmv_radiusChanged
  - gmv_zoom
  - gmv_zoomChanged
  - gmv_latLng
  - gmv_latLngChanged
  - gmv_heatMap
  - gmv_mapClickListener
  - gmv_mapLoadedCallback
  - gmv_mapLongClickListener
  - gmv_myLocationButtonClickListener
  - gmv_indoorStateChangeListener
  - gmv_poiClickListener
  - gmv_snapshotReadyCallback
```
- [Markers Bindings]:
```
  - gmv_mapItems
  - gmv_markerClickListener
  - gmv_infoWindowAdapter
  - gmv_infoWindowClickListener
  - gmv_infoWindowCloseListener
  - gmv_infoWindowLongClickListener
  - gmv_markerDragListener
```
- [Overlays Bindings]:
```
  - gmv_groundOverlays
  - gmv_groundOverlayClickListener
```
- [Polygons Bindings]:
```
  - gmv_polygons
  - gmv_polygonClickListener
```
- [Map Bindings]:
```java
  - gmv_polylines
  - gmv_polylineClickListener
```

## Getting Help
To report a specific problem or feature request, [open a new issue on Github].

## Company
[Here](https://github.com/TangoAgency/) you can see open source work developed by Tango Agency.

Whether you're searching for a new partner or trusted team for creating your new great product we are always ready to start work with you.

You can contact us via contact@tango.agency.
Thanks in advance.

[Android Data Binding]: <https://developer.android.com/topic/libraries/data-binding/index.html>
[Android Data Binding Setup]: <https://developer.android.com/topic/libraries/data-binding/index.html#build_environment>
[open a new issue on Github]: <https://github.com/TangoAgency/Viking/issues/new>
[MapAwareActivityView]: <https://github.com/TangoAgency/Viking/blob/feature/map-bindings/viking-map-aware-views/src/main/java/net/droidlabs/vikingmap/views/MapAwareActivityView.java>
[MapAwareFragmentView]: <https://github.com/TangoAgency/Viking/blob/feature/map-bindings/viking-map-aware-views/src/main/java/net/droidlabs/vikingmap/views/MapAwareFragmentView.java>
[Camera Bindings]: <https://github.com/TangoAgency/Viking/blob/feature/map-bindings/viking-mapbindings/src/main/java/agency/tango/viking/bindings/map/bindings/CameraBindings.java>
[Circle Bindings]: <https://github.com/TangoAgency/Viking/blob/feature/map-bindings/viking-mapbindings/src/main/java/agency/tango/viking/bindings/map/bindings/CircleBindings.java>
[Cluster Bindings]: <https://github.com/TangoAgency/Viking/blob/feature/map-bindings/viking-mapbindings/src/main/java/agency/tango/viking/bindings/map/bindings/ClusterBindings.java>
[Map Bindings]: <https://github.com/TangoAgency/Viking/blob/feature/map-bindings/viking-mapbindings/src/main/java/agency/tango/viking/bindings/map/bindings/MapBindings.java>
[Markers Bindings]: <https://github.com/TangoAgency/Viking/blob/feature/map-bindings/viking-mapbindings/src/main/java/agency/tango/viking/bindings/map/bindings/MarkerBindings.java>
[Overlays Bindings]: <https://github.com/TangoAgency/Viking/blob/feature/map-bindings/viking-mapbindings/src/main/java/agency/tango/viking/bindings/map/bindings/OverlaysBindings.java>
[Polygons Bindings]: <https://github.com/TangoAgency/Viking/blob/feature/map-bindings/viking-mapbindings/src/main/java/agency/tango/viking/bindings/map/bindings/PolygonBindings.java>
[Polyline Bindings]: <https://github.com/TangoAgency/Viking/blob/feature/map-bindings/viking-mapbindings/src/main/java/agency/tango/viking/bindings/map/bindings/PolylineBindings.java>
[xml]: <https://github.com/TangoAgency/Viking/blob/feature/map-bindings/example/src/main/res/layout/activity_map.xml>
[GoogleMapView]: <https://github.com/TangoAgency/Viking/blob/feature/map-bindings/viking-mapbindings/src/main/java/agency/tango/viking/bindings/map/GoogleMapView.java>
[MapViewModel]: <https://github.com/TangoAgency/Viking/blob/feature/map-bindings/example/src/main/java/agency/tango/viking/example/MapViewModel.java>
[MapActivity]: <https://github.com/TangoAgency/Viking/blob/feature/map-bindings/example/src/main/java/agency/tango/viking/example/MapActivity.java>
[activity_map.xml]: <https://github.com/TangoAgency/Viking/blob/feature/map-bindings/example/src/main/res/layout/activity_map.xml>
[Viking ViewModel]: <https://github.com/TangoAgency/Viking/tree/master/viking-viewmodel>