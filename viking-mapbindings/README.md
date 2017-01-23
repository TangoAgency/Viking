# Viking Map Bindings
Viking Map Bindings is a library for using Google MapView with Android Data Binding with many API improvements.

### To use this library, please read this: [Android Data Binding]
## Usage in project
### Step 1:
Enable Data Binding in your project [Android Data Binding Setup]
### Step 2:
Make your Activity extend  [MapAwareActivityView] (for Fragment use [MapAwareFragmentView])
```java
public class MapActivity extends MapAwareActivityView<VM extends ViewModel, VD extends ViewDataBinding>
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


### Binding examples
This section will introduce you how to bind objects to [GoogleMapView]
#### Complex example
Example based on [Viking ViewModel]:
  - [MapViewModel]
  - [MapActivity]
  - [activity_map.xml]

#### Binding markers
If you want to bind list of markers you need to create ObservableList in your ViewModel.
With this library you are allowed to bind any model you want to the specific marker.
```java
private ObservableList<BindableMarker<ExampleModel>> models = new ObservableArrayList<>();
```
Secondly you need to add items to list.
```java
    models.add(new BindableMarker<>(
        new ExampleModel("Hello", "World"),
        0,
        new MarkerOptions()
            .title("marker")
            .position(new LatLng(3, 3))));
```
Before last step you need to make ObservableList bindable.
```java
  @Bindable
  public Collection<BindableMarker<ExampleModel>> getModels() {
    return models;
  }
```
Now just bind list in your xml view
```
  <agency.tango.viking.bindings.map.GoogleMapView
      android:id="@+id/map"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      bind:gmv_markers="@{viewModel.models}"
      />
```

#### Binding click listeners
To bind listener to any object to any object you need to do two things. In this example I will bind markers click listener.

First you need to add proper ```@Bindable``` method to your class
```java
  @Bindable
  public OnMarkerClickListener<BindableMarker<ExampleModel>> getMarkerClickListener() {
    return new OnMarkerClickListener<BindableMarker<ExampleModel>>() {
      @Override
      public boolean onClick(BindableMarker<ExampleModel> item) {
        item.getMarker().setPosition(new LatLng(20, 20));
        item.getMarker().showInfoWindow();
        return true;
      }
    };
  }
```
And the second step is binding listener in xml view
```
  <agency.tango.viking.bindings.map.GoogleMapView
      android:id="@+id/map"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      bind:gmv_markerClickListener="@{viewModel.markerClickListener}"
      />
```

#### Binding window info adapters
With this library you are allowed to use WindowInfoAdapterFactory which returns binded data for given marker or cluster item.
This example will present how to user WindowInfoAdapterFactory for markers.

First add ```@Bindable``` method in your class
```java
  @Bindable
  public InfoWindowAdapterFactory<BindableMarker<ExampleModel>> getInfoWindowAdapter() {
    return new InfoWindowAdapterFactory<BindableMarker<ExampleModel>>() {
      @Override
      public CustomInfoWindowAdapter<BindableMarker<ExampleModel>> createInfoWindowAdapter(
          final Context context) {
        return new CustomInfoWindowAdapter<BindableMarker<ExampleModel>>() {
          @Override
          public View getInfoWindow(BindableMarker<ExampleModel> bindableMarker) {
            return null;
          }

          @Override
          public View getInfoContents(BindableMarker<ExampleModel> bindableMarker) {
            View view = LayoutInflater.from(context).inflate(R.layout.info_window, null);

            TextView title = (TextView) view.findViewById(R.id.tv_title);
            TextView description = (TextView) view.findViewById(R.id.tv_description);

            title.setText(bindableMarker.getObject().getTitle());
            description.setText(bindableMarker.getObject().getDescription());
            return view;
          }
        };
      }
    };
  }
```
Then add proper attribute in xml view.
```
  <agency.tango.viking.bindings.map.GoogleMapView
      android:id="@+id/map"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      bind:gmv_infoWindowAdapter="@{viewModel.infoWindowAdapter}"
      />
```

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
- [Cluster Bindings]  (for clustering please implement your model with ClusterMapItem):

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
  - gmv_markers
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

```
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