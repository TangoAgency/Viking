# Viking Map Bindings

Viking Map Bindings is a library for using Google MapView with Android Data Binding with many API improvements.

### To use this library, please read this: [Android Data Binding][Android Data Binding]
## Usage 

### Step 1:

Enable Data Binding in your project [Android Data Binding Setup]
### Step 2:
Make your Activity extend  [MapAwareActivityView][MapAwareActivityView] (for Fragment use [MapAwareFragmentView][MapAwareFragmentView])
```java
public class MapActivity extends MapAwareActivityView<VM extends ViewModel, VD extends ViewDataBinding>
```
### Step 3:
Add to your [xml][activity_map.xml]
```xml
 <agency.tango.viking.bindings.map.GoogleMapView
      android:id="@+id/map"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      />
```
### Step 4:
Bind EVERYTHING!!!

## Complex example

Example based on [Viking ViewModel][Viking ViewModel]:

- [MapViewModel][MapViewModel]
- [MapActivity][MapActivity]
- [activity_map.xml][activity_map.xml]

## Marker Bindings
#### - **```gmv_markers```** - Binding list of markers to the map

To bind the list of markers you need to create a List in your java class. With this library, you are allowed to bind any model you want to the specific marker.

#####Please keep in mind that using ObservableList is recommended if you are using mutable list of objects which will be binded (updating, creating new objects and removing old one will be automatically handled by this library)

```java
private ObservableList<BindableMarker<ExampleModel>> models = new ObservableArrayList<>();
```
Secondly you need to add items to list.
```java
    models.add(new BindableMarker<>(
        new ExampleModel("Hello", "World"),
        new MarkerOptions()
            .title("marker")
            .position(new LatLng(3, 3))));
```
Before last step you need to make List bindable.
```java
  @Bindable
  public Collection<BindableMarker<ExampleModel>> getModels() {
    return models;
  }
```
Now just bind list in your XML view
```xml
  <agency.tango.viking.bindings.map.GoogleMapView
      bind:gmv_markers="@{viewModel.models}"/>
```

#### - **```gmv_markerClickListener```** Bind on click listener to marker.


To bind OnClickListener to the marker first you need to create a ```@Bindable``` method in your java class which will return OnMarkerClickListener ```<BindableMarker<YourModel>>```. With OnMarkerClickListener you have easy access to model assigned to the marker.

```java
@Bindable
public OnMarkerClickListener<BindableMarker<ExampleModel>> getMarkerClickListener() {
  return new OnMarkerClickListener<BindableMarker<ExampleModel>>() {
    @Override
    public boolean onClick(BindableMarker<ExampleModel> item) {
      item.getMarker().showInfoWindow();
      return true;
    }
  };
}
```
  Now you need to bind that in XML

```xml
   <agency.tango.viking.bindings.map.GoogleMapView
            bind:gmv_markerClickListener="@{viewModel.markerClickListener}"/>
```

#### - **```gmv_infoWindowAdapter```** Bind InfoWindowAdapter to markers.


To bind InfoWindowAdapter to the markers you need to create a ```@Bindable``` method in your java class which will return InfoWindowAdapterFactory<BindableMarker<YourModel>> which returns CustomInfoWindowAdapter. 

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

Then add to your XML

```xml
<agency.tango.viking.bindings.map.GoogleMapView
      bind:gmv_infoWindowAdapter="@{viewModel.infoWindowAdapter}" />
```

#### - **```gmv_infoWindowClickListener  ```** Set on info window click listener.

If you want to add on info window click listener add a ```@Bindable``` method in your java class which will return ItemClickListener<BindableMarker<YourModel>>.  

```java
  @Bindable
  public ItemClickListener<BindableMarker<ExampleModel>> getInfoWindowClickListener() {
    return new ItemClickListener<BindableMarker<ExampleModel>>() {
      @Override
      public void onClick(BindableMarker<ExampleModel> item) {
        item.getMarker().hideInfoWindow();
      }
    };
  }
```

Then add to your XML

```xml
     <agency.tango.viking.bindings.map.GoogleMapView
 		bind:gmv_infoWindowClickListener="@{viewModel.infoWindowClickListener}" />
```

#### - **```gmv_infoWindowCloseListener```** Set on info window closed listener

  To set ```ItemClickListener``` you need to create a ```@Bindable``` method in your java class which will return ```ItemClickListener```

```java
@Bindable
public ItemClickListener<ExampleModel> getInfoWindowCloseListener()
{
  return new ItemClickListener<ExampleModel>() {
    @Override
    public void onClick(ExampleModel item) {
      ...
    }
  };
}
```

Then add to your XML

```xml
 <agency.tango.viking.bindings.map.GoogleMapView
 		bind:gmv_infoWindowCloseListener="@{viewModel.infoWindowCloseListener}" />
```

#### - **```gmv_infoWindowLongClickListener```** Set on info window long click listener


To set ```ItemClickListener```you need to create a ```@Bindable``` method which will return ```ItemClickListener```

```java
  @Bindable
public ItemClickListener<ExampleModel> getInfoWindowLongClickListener()
{
  return new ItemClickListener<ExampleModel>() {
    @Override
    public void onClick(ExampleModel item) {
      ...
    }
  };
}
```

Then add to your XML

```xml
<agency.tango.viking.bindings.map.GoogleMapView
 		bind:gmv_infoWindowLongClickListener="@{viewModel.infoWindowLongClickListener}" />
```

#### - **```gmv_markerDragListener```** Set marker drag listener

To set on marker drag listener you need to create a ```@Bindable``` method in your java class which will return ```MarkerDragListener```

```java
@Bindable
  public MarkerDragListener<ExampleModel> getMarkerDragListener()
  {
    return new MarkerDragListener<ExampleModel>() {
      @Override
      public void onMarkerDragStart(BindableMarker<ExampleModel> marker) {
        
      }

      @Override
      public void onMarkerDrag(BindableMarker<ExampleModel> marker) {

      }

      @Override
      public void onMarkerDragEnd(BindableMarker<ExampleModel> marker) {

      }
    };
  }
```

Then add to your XML

```xml
<agency.tango.viking.bindings.map.GoogleMapView
 		bind:gmv_markerDragListener="@{viewModel.markerDragListener}" />
```

## Camera Bindings

#### - **```gmv_cameraMoveStartedListener```** Set on camera move started listener
To set on camera move started listener you need to create a ```@Bindable``` method in your java class which will return OnCameraMoveStartedListener

``` java
  @Bindable
  public GoogleMap.OnCameraMoveStartedListener getOnCameraMoveStartedListener(){
    return new GoogleMap.OnCameraMoveStartedListener(){
      ...
    }
  }
```

Then add to your XML

```xml
<agency.tango.viking.bindings.map.GoogleMapView
 		bind:gmv_cameraMoveStartedListener="@{viewModel.cameraMoveStartedListener}" />
```

#### - **```gmv_cameraMoveCanceledListener```** Set on camera move cancelled listener


To set on camera move cancelled listener you need to create a ```@Bindable``` method in your java class which will return OnCameraMoveCanceledListener

```java
  @Bindable
  public GoogleMap.OnCameraMoveCanceledListener getOnCameraMoveCanceledListener() {
  	return new GoogleMap.OnCameraMoveCanceledListener(){
      ...
  	}
  }
```
 Then add to your XML

```xml
<agency.tango.viking.bindings.map.GoogleMapView
 		bind:gmv_cameraMoveCanceledListener="@{viewModel.cameraMoveCanceledListener}" />
```

#### - **```gmv_cameraIdleListener```** Add on camera idle listener

To add on camera idle listener you need to create a ```@Bindable``` method in your java class which will return OnCameraIdleListener

```java
  @Bindable
  public GoogleMap.OnCameraIdleListener getOnCameraIdleListener() {
  	return new GoogleMap.OnCameraIdleListener(){
      ...
  	}
  }
```

 Then add to your XML

```xml
<agency.tango.viking.bindings.map.GoogleMapView
 		bind:gmv_cameraIdleListener="@{viewModel.cameraIdleListener}" />
```

#### - **```gmv_cameraMoveListener```** Set on camera move listener

To add on camera move listener you need to create a ```@Bindable``` method in your java class which will return OnCameraMoveListener

```java
  @Bindable
  public GoogleMap.OnCameraMoveListener getOnCameraMoveListener() {
  	return new GoogleMap.OnCameraMoveListener(){
      ...
  	}
  }
```

 Then add to your XML

```xml
<agency.tango.viking.bindings.map.GoogleMapView
 		bind:gmv_cameraMoveListener="@{viewModel.cameraMoveListener}" />
```


## Circle Bindings 

#### - **```gmv_circles```** - Binding list of circles to the map
To bind a list of circles you need to create a List in your ViewModel.

#####Please keep in mind that using ObservableList is recommended if you are using mutable list of objects which will be binded (updating, creating new objects and removing old one will be automatically handled by this library)

```java
private ObservableList<BindableCircle> circles = new ObservableArrayList<>();
```
Then you need to add items to list.
```java
      circles.add(new BindableCircle(new CircleOptions()
        .radius(100000)
        .clickable(true)
        .center(new LatLng(0, 5))));
```
Before last step you need to make List bindable.
```java
  @Bindable
  public Collection<BindableCircle> getCircles() {
    return circles;
  }
```
Now just bind list in your XML view
```xml
  <agency.tango.viking.bindings.map.GoogleMapView
      bind:gmv_circles="@{viewModel.circles}"/>
```

#### - **```gmv_circleClickListener```** Bind on click listener to circle.


To bind OnClickListener to the circle first you need to create a ```@Bindable``` method in your java class which will return ```ItemClickListener<BindableCircle>```.

```java
 @Bindable
  public ItemClickListener<BindableCircle> getCircleClickListener() {
    return new ItemClickListener<BindableCircle>() {
      @Override
      public void onClick(BindableCircle item) {
        item.getCircle().setFillColor(R.color.red);
      }
    };
  }
```
  Now you need to bind that in XML

```xml
   <agency.tango.viking.bindings.map.GoogleMapView
            bind:gmv_circleClickListener="@{viewModel.circleClickListener}"/>
```

## GroundOverlays Bindings 

#### - **```gmv_groundOverlays```** - Binding list of groundOverlays to the map

To bind a list of groundOverlays you need to create a List in your java class.

#####Please keep in mind that using ObservableList is recommended if you are using mutable list of objects which will be binded (updating, creating new objects and removing old one will be automatically handled by this library)

```java
private ObservableList<BindableOverlay> groundOverlays = new ObservableArrayList<>();
```
Then you need to add items to list.
```java
groundOverlays.add(new BindableOverlay(
  new GroundOverlayOptions()
  	.image(BitmapDescriptorFactory.fromResource(R.drawable.heart))
  	.positionFromBounds(new LatLngBounds(new LatLng(0, -4), new LatLng(1, -3)))));
```
Before last step you need to make List bindable.
```java
  @Bindable
  public Collection<BindableOverlay> getGroundOverlays() {
    return groundOverlays;
  }
```
Now just bind list in your XML view
```xml
  <agency.tango.viking.bindings.map.GoogleMapView
      bind:gmv_groundOverlays="@{viewModel.groundOverlays}"/>
```

#### - **```gmv_groundOverlayClickListener```** Bind on click listener to ground overlay.


To bind OnClickListener to the ground overlay first you need to create a ```@Bindable``` method in your java class which will return ```ItemClickListener<BindableCircle>```.

```java
 @Bindable
  public ItemClickListener<BindableOverlay> getGroundOverlayClickListener() {
    return new ItemClickListener<BindableOverlay>() {
      @Override
      public void onClick(BindableOverlay item) {
        item.getGroundOverlay().setPosition(new LatLng(0, 0));
      }
    };
  }
```
  Now you need to bind that in XML

```xml
   <agency.tango.viking.bindings.map.GoogleMapView
            bind:gmv_groundOverlayClickListener="@{viewModel.groundOverlayClickListener}"/>
```

## Polygons Bindings 

#### - **```gmv_polygons```** - Binding list of polygons to the map

To bind a list of polygons you need to create a List in your java class.

#####Please keep in mind that using ObservableList is recommended if you are using mutable list of objects which will be binded (updating, creating new objects and removing old one will be automatically handled by this library)

```java
private ObservableList<BindablePolygon> polygons = new ObservableArrayList<>();
```
Then you need to add items to list.
```java
polygons.add(new BindablePolygon(new PolygonOptions()
                                 .clickable(true)
                                 .strokeColor(Color.rgb(255, 0, 0))
                                 .add(new LatLng(0, 0))
                                 .add(new LatLng(0, 1))
                                 .add(new LatLng(1, 1))
                                 .add(new LatLng(1, 0))));
```
Before last step you need to make List bindable.
```java
  @Bindable
  public Collection<BindablePolygon> getPolygons() {
    return polygons;
  }
```
Now just bind list in your XML view
```xml
  <agency.tango.viking.bindings.map.GoogleMapView
      bind:gmv_polygons="@{viewModel.polygons}"/>
```

#### - **```gmv_polygonClickListener```** Bind on click listener to polygony.


To bind OnClickListener to the polygon first you need to create a ```@Bindable``` method in your java class which will return ```ItemClickListener<BindablePolygon>```.

```java
 @Bindable
  public ItemClickListener<BindablePolygon> getPolygonClickListener() {
    return new ItemClickListener<BindablePolygon>() {
      @Override
      public void onClick(BindablePolygon item) {
        item.getPolygon().setFillColor(R.color.red);
      }
    };
  }
```
  Now you need to bind that in XML

```xml
   <agency.tango.viking.bindings.map.GoogleMapView
            bind:gmv_polygonClickListener="@{viewModel.polygonClickListener}"/>
```

## Polylines Bindings 

#### - **```gmv_polylines```** - Binding list of polylines to the map

To bind a list of polylines you need to create a List in your java class.

#####Please keep in mind that using ObservableList is recommended if you are using mutable list of objects which will be binded (updating, creating new objects and removing old one will be automatically handled by this library)

```java
private ObservableList<BindablePolyline> polylines = new ObservableArrayList<>();
```
Then you need to add items to list.
```java
  polylines.add(new BindablePolyline(
    new PolylineOptions()
    .clickable(true)
    .add(new LatLng(0, 25))
    .add(new LatLng(0, 30))));
```
Before last step you need to make List bindable.
```java
  @Bindable
  public Collection<BindablePolyline> getPolylines() {
    return polylines;
  }
```
Now just bind list in your XML view
```xml
  <agency.tango.viking.bindings.map.GoogleMapView
      bind:gmv_polylines="@{viewModel.polylines}"/>
```

#### - **```gmv_polylineClickListener```** Bind on click listener to polyline.

####To bind OnClickListener to the polyline first you need to create a ```@Bindable``` method in your java class which will return ```ItemClickListener<BindablePolyline>```.

```java
 @Bindable
  public ItemClickListener<BindablePolyline> getPolylineClickListener() {
    return new ItemClickListener<BindablePolyline>() {
      @Override
      public void onClick(BindablePolyline item) {
        item.getPolyline().setWidth(50f);
      }
    };
  }
```
  Now you need to bind that in XML

```xml
   <agency.tango.viking.bindings.map.GoogleMapView
            bind:gmv_polylineClickListener="@{viewModel.polylineClickListener}"/>
```

## Cluster bindings

- **```gmv_clusterItems```** Bind a list of the cluster items to the map

To bind a list of cluster item you need to create a ```List<T extends ClusterItem>``` in your java class.

#####Please keep in mind that using ObservableList is recommended if you are using mutable list of objects which will be binded (updating, creating new objects and removing old one will be automatically handled by this library)

```java
private final ObservableList<ClusterModel> clusterItems = new ObservableArrayList<>();
```
Then you need to add items to the list.
```java
clusterItems.add(new ClusterModel(new LatLng(0, 12.202222)));
```
Before last step you need to make List bindable.
```java
  @Bindable
  public Collection<ClusterModel> getClusterItems() {
    return clusterItems;
  }
```
Now just bind list in your XML view
```xml
  <agency.tango.viking.bindings.map.GoogleMapView
      bind:gmv_clusterItems="@{viewModel.clusterItems}"/>
```

- **```gmv_rendererFactory```** Bind renderer factory for customizing markers via clustering

To bind custom renderer to the cluster item you need to create a ```@Bindable``` method in your java class which will return ```RendererFactory<YourClusterModel>```.

```java
  @Bindable
  public RendererFactory<ClusterModel> getRendererFactory() {
    return new RendererFactory<ClusterModel>() {
      @Override
      public ClusterRenderer<ClusterModel> createRenderer(Context context, GoogleMap googleMap,
          ClusterManager<ClusterModel> clusterManager) {
        return new CustomClusterRenderer(context, googleMap, clusterManager);
      }
    };
  }
```

Note that ```CustomClusterRenderer``` need to implement ```ClusterRenderer<T>``` or extend ```DefaultClusterRenderer```.
Then add to your XML

```xml
<agency.tango.viking.bindings.map.GoogleMapView
    bind:gmv_rendererFactory="@{viewModel.rendererFactory }"	/>
```

- **```gmv_algorithm```** Bind algorithm for clustering items


To bind custom algorithm you need to create a ```@Bindable``` method in your java class which will return an ```Algorithm``` object

```java
  @Bindable
  public Algorithm<ClusterModel> getAlgorithm()
  {
    return new NonHierarchicalDistanceBasedAlgorithm<>();
  }
```

Then add to your XML

```xml
<agency.tango.viking.bindings.map.GoogleMapView
    bind:gmv_algorithm="@{viewModel.algorithm }"	/>
```

#### - **```gmv_clusterItemInfoWindowAdapter```** Bind InfoWindowAdapter to cluster item.

  To bind InfoWindowAdapter to the cluster item you need to create a ```@Bindable``` method in your java class which will return InfoWindowAdapterFactory<BindableMarker<YourClusterItem>> which returns CustomInfoWindowAdapter. 

```java
    @Bindable
    public InfoWindowAdapterFactory<ClusterItem> getClusterItemInfoWindowAdapter() {
      return new InfoWindowAdapterFactory<ClusterItem>() {
        @Override
        public CustomInfoWindowAdapter<ClusterItem> createInfoWindowAdapter(
            final Context context) {
          return new CustomInfoWindowAdapter<ClusterItem>() {
            @Override
            public View getInfoWindow(ClusterItem clusterItem) {
              return null;
            }

            @Override
            public View getInfoContents(ClusterItem clusterItem) {
              View view = LayoutInflater.from(context).inflate(R.layout.info_window, null);
              TextView title = (TextView) view.findViewById(R.id.tv_title);

              title.setText(String.format("LatLng: %s", clusterItem.getPosition().toString()));
              return view;
            }
          };
        }
      };
    }

```

  Then add to your XML

```xml
  <agency.tango.viking.bindings.map.GoogleMapView
        bind:gmv_clusterItemInfoWindowAdapter="@{viewModel.clusterItemInfoWindowAdapter}" />
```

#### - **```gmv_clusterInfoWindowAdapter```** Bind InfoWindowAdapter to cluster.


  To bind InfoWindowAdapter to the cluster you need to create a ```@Bindable``` method in your java class which will return InfoWindowAdapterFactory<BindableMarker<Cluster>> which returns CustomInfoWindowAdapter. 

```java
  @Bindable
  public InfoWindowAdapterFactory<StaticCluster> getClusterInfoWindowAdapter() {
    return new InfoWindowAdapterFactory<StaticCluster>() {
      @Override
      public CustomInfoWindowAdapter<StaticCluster> createInfoWindowAdapter(final Context context) {
        return new CustomInfoWindowAdapter<StaticCluster>() {
          @Override
          public View getInfoWindow(StaticCluster cluster) {
            return null;
          }

          @Override
          public View getInfoContents(StaticCluster cluster) {
            View view = LayoutInflater.from(context).inflate(R.layout.info_window, null);

            TextView title = (TextView) view.findViewById(R.id.tv_title);
            TextView description = (TextView) view.findViewById(R.id.tv_description);

            title.setText(String.format("SIZE: %d", cluster.getItems().size()));
            description.setText(String.format("LatLng: %f %f", cluster.getPosition().latitude,
                cluster.getPosition().longitude));
            return view;
          }
        };
      }
    };
  }
```

  Then add to your XML

```xml
  <agency.tango.viking.bindings.map.GoogleMapView
        bind:gmv_clusterInfoWindowAdapter="@{viewModel.clusterInfoWindowAdapter}" />
```

#### - **```gmv_clusterClickListener```** Bind on cluster click listener
To bind OnClusterClickListener you need to create a ```@Bindable``` method which will return OnClusterClickListener

```java
  @Bindable
  public ClusterManager.OnClusterClickListener getClusterClickListener() {
    return new ClusterManager.OnClusterClickListener() {
      @Override
      public boolean onClusterClick(Cluster cluster) {
        setLatLng(cluster.getPosition());
        return false;
      }
    };
  }
```

Then add to your XML

```xml
  <agency.tango.viking.bindings.map.GoogleMapView
        bind:gmv_clusterClickListener="@{viewModel.clusterClickListener}" />
```

#### - **```gmv_clusterItemClickListener```** Bind on cluster item click listener

  To bind OnClusterItemClickListener you need to create a ```@Bindable``` method which will return OnClusterItemClickListener.

```java
    @Bindable
    public ClusterManager.OnClusterItemClickListener<ClusterItem> getOnClusterItemClickListener()
    {
      return  new ClusterManager.OnClusterItemClickListener<ClusterItem>() {
        @Override
        public boolean onClusterItemClick(ClusterItem clusterItem) {
          Toast.makeText(getContext(), clusterItem.getPosition(), Toast.LENGTH_SHORT);  
          return false;
        }
      };
    }
```

  Then add to your XML

```xml
  <agency.tango.viking.bindings.map.GoogleMapView
      bind:gmv_clusterItemClickListener ="@{viewModel.clusterItemClickListener }" />
```

#### - **```gmv_clusterInfoWindowClickListener```** Bind on cluster info window click listener

  To bind OnClusterInfoWindowClickListener you need to create a ```@Bindable``` method which will return OnClusterInfoWindowClickListener

```java
@Bindable	
public ClusterManager.OnClusterInfoWindowClickListener<ClusterItem> 								getOnClusterInfoWindowClickListener()  {
  return new ClusterManager.OnClusterInfoWindowClickListener<ClusterItem>() {
    @Override
    public void onClusterInfoWindowClick(Cluster<ClusterItem> cluster) {
      cluster.getItems().clear();
    }
  };
}
```

Then add to your XML

```xml
<agency.tango.viking.bindings.map.GoogleMapView
    bind:gmv_clusterInfoWindowClickListener="@{viewModel.clusterInfoWindowClickListener}" />
```

#### - **```gmv_clusterItemInfoWindowClickListener```** Bind on cluster item info window click listener

To bind OnClusterItemInfoWindowClickListener you need to create a ```@Bindable``` method which will return OnClusterItemInfoWindowClickListener

```java
@Bindable
public ClusterManager.OnClusterItemInfoWindowClickListener<ClusterItem> 							getOnClusterItemInfoWindowClickListener() {
  return new ClusterManager.OnClusterItemInfoWindowClickListener<ClusterItem>() {
    @Override
    public void onClusterItemInfoWindowClick(ClusterItem clusterItem) {
      Toast.makeText(getContext(), clusterItem.getPosition(), Toast.LENGTH_SHORT);
    }
  };
}
```

Then add to your XML

```xml
<agency.tango.viking.bindings.map.GoogleMapView
    bind:gmv_clusterInfoWindowClickListener="@{viewModel.clusterInfoWindowClickListener}" />
```



## Map Bindings

#### - **```gmv_zoom```** Two-way data binding for zoom. With this binding, you can set and retrieve current map zoom.

First, add field in java class

```java
private float zoom;
```

For retrieving zoom add a ```@Bindable``` method in your java class

```java
@Bindable
public float getZoom(){
  return zoom;
}
```

For setting zoom add method

```java
public void setZoom(float zoom) {
  this.zoom = zoom;
  notifyPropertyChanged(BR.zoom);
}
```

Then add to your XML

```xml
<agency.tango.viking.bindings.map.GoogleMapView
    bind:gmv_zoom="@{viewModel.zoom}" />
```

#### - **```gmv_radius```** Two-way data binding for the radius. With this binding, you can set and retrieve current map radius (This means that you get max width or max height of GoogleMap depending on device orientation).

First, add field in java class

```java
private int radius;
```

For retrieving radius add a ```@Bindable``` method in your java class

```java
@Bindable
public int getRadius(){
  return radius;
}
```

For setting radius add method

```java
public void setRadius(int radius) {
  this.radius = radius;
  notifyPropertyChanged(BR.radius);
}
```

Then add to your XML

```xml
<agency.tango.viking.bindings.map.GoogleMapView   					   					        	bind:gmv_radius="@{viewModel.radius}" />
```

#### - **```gmv_latLng ```**Two-way data binding for latLng. With this binding, you can set and retrieve center of the map.

First, add field in java class

```java
private LatLng latLng;
```

For retrieving radius add a ```@Bindable``` method in your java class

```java
@Bindable
public LatLng getLatLng(){
  return latLng;
}
```

For setting radius add method

```java
public void setLatLng(LatLng latLng) {
  this.latLng = latLng;
  notifyPropertyChanged(BR.latLng);
}
```

Then add to your XML

```xml
<agency.tango.viking.bindings.map.GoogleMapView
    bind:gmv_latLng="@{viewModel.latLng}" />
```

#### - **```gmv_heatMap```** Bind heat map to GoogleMapView.

To bind HeatMap you need to create a ```@Bindable``` method which will return ```HeatmapTileProvider```

```java
public HeatmapTileProvider getHeatMap(){
  return new HeatmapTileProvider.Builder()
        .data(Arrays.asList(new LatLng(0, 0),
            new LatLng(0, 1),
            new LatLng(0, 2)))
        .build(); 
}
```

Then add to your XML

```xml
<agency.tango.viking.bindings.map.GoogleMapView
    bind:gmv_heatMap="@{viewModel.heatMap}" />
```

#### - **```gmv_mapClickListener```** Bind on map click listener.

To bind ```OnMapClickListener``` you need to create a ```@Bindable``` method which will return ```OnMapClickListener```

```java
@Bindable
public GoogleMap.OnMapClickListener getMapClickListener()
{
  return new GoogleMap.OnMapClickListener() {
    @Override
    public void onMapClick(LatLng latLng) {
      ...
    }
  };
}
```

Then add to your XML

```xml
<agency.tango.viking.bindings.map.GoogleMapView
    bind:gmv_mapClickListener="@{viewModel.mapClickListener}" />
```

#### - **```gmv_mapLoadedCallback```** Bind on map loaded callback

  To bind ```OnMapLoadedCallback``` you need to create a ```@Bindable``` method which will return ```OnMapLoadedCallback```

```java
@Bindable
public GoogleMap.OnMapLoadedCallback getMapLoadedCallback()
{
  return new GoogleMap.OnMapLoadedCallback() {
    @Override
    public void onMapLoaded() {

    }
  }
}
```

Then add to your XML

```xml
<agency.tango.viking.bindings.map.GoogleMapView
    bind:gmv_mapLoadedCallback="@{viewModel.mapLoadedCallback}" />
```

#### - **```gmv_mapLongClickListener```** Bind on map long click listener.

To bind ```OnMapLongClickListener``` you need to create a ```@Bindable``` method which will return ```OnMapLongClickListener```

```java
@Bindable
public GoogleMap.OnMapLongClickListener getMapLongClickListener()
{
  return new GoogleMap.OnMapLongClickListener() {
    @Override
    public void onMapLongClick(LatLng latLng) {
      ...
    }
  };
}
```

Then add to your XML

```xml
<agency.tango.viking.bindings.map.GoogleMapView
    bind:gmv_mapLongClickListener="@{viewModel.mapLongClickListener}" />
```

#### - **```gmv_myLocationButtonClickListener```** Bind on my location button click listener.

To bind ```OnMyLocationButtonClickListener``` you need to create a ```@Bindable``` method which will return ```OnMyLocationButtonClickListener```

```java
@Bindable
public GoogleMap.OnMyLocationButtonClickListener getMyLocationButtonClickListene()
{
  return new GoogleMap.OnMyLocationButtonClickListener() {
    @Override
    public boolean onMyLocationButtonClick() {
      	...
        return false;
    }
  };
}
```

Then add to your XML

```xml
<agency.tango.viking.bindings.map.GoogleMapView
    bind:gmv_myLocationButtonClickListener="@{viewModel.myLocationButtonClickListener}" />
```

#### - **```gmv_indoorStateChangeListener```** Bind on indoor state change listener.

To bind ```OnIndoorStateChangeListener``` you need to create a ```@Bindable``` method which will return ```OnIndoorStateChangeListener```

```java
@Bindable
  public GoogleMap.OnIndoorStateChangeListener getIndoorStateChangeListener(){
    return new GoogleMap.OnIndoorStateChangeListener() {
      @Override
      public void onIndoorBuildingFocused() {
          ...
      }

      @Override
      public void onIndoorLevelActivated(IndoorBuilding indoorBuilding) {
          ...
      }
    };
  }
```

Then add to your XML

```xml
<agency.tango.viking.bindings.map.GoogleMapView
  bind:gmv_indoorStateChangeListener="@{viewModel.indoorStateChangeListener}" />
```

#### - **```gmv_poiClickListener```** Bind on poi click listener.

To bind ```OnPoiClickListener``` you need to create a @Bindable method which will return ```OnPoiClickListener```

```java
@Bindable
public GoogleMap.OnPoiClickListener getPoiClickListener(){
	return new GoogleMap.OnPoiClickListener() {
		@Override
		public void onPoiClick(PointOfInterest pointOfInterest) {
			...
		}
	};
}
```

Then add to your XML

```xml
<agency.tango.viking.bindings.map.GoogleMapView
   bind:gmv_poiClickListener="@{viewModel.poiClickListener}" />
```
#### - **```gmv_snapshotReadyCallback```** Bind on snapshot ready callback.

  To bind ```SnapshotReadyCallback``` you need to create a @Bindable method which will return ```SnapshotReadyCallback```

```java
  @Bindable
  public GoogleMap.SnapshotReadyCallback getSnapshotReadyCallback() {
    return new GoogleMap.SnapshotReadyCallback() {
      @Override
      public void onSnapshotReady(Bitmap bitmap) {
        ...   
      }
    };
  }
```

Then add to your XML

```xml
<agency.tango.viking.bindings.map.GoogleMapView
    bind:gmv_snapshotReadyCallback="@{viewModel.snapshotReadyCallback}" />
```

## Available Bindings
- [Camera Bindings][Camera Bindings]:

```
  - gmv_cameraMoveStartedListener
  - gmv_cameraMoveCanceledListener
  - gmv_cameraIdleListener
  - gmv_cameraMoveListener
```
- [Circle Bindings][Circle Bindings]:

```
  - gmv_circles
  - gmv_circleClickListener
```
- [Cluster Bindings][Cluster Bindings]  (for clustering please implement your model with ClusterMapItem):

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
- [Map Bindings][Map Bindings]:

```
  - gmv_radius
  - gmv_zoom
  - gmv_latLng
  - gmv_heatMap
  - gmv_mapClickListener
  - gmv_mapLoadedCallback
  - gmv_mapLongClickListener
  - gmv_myLocationButtonClickListener
  - gmv_indoorStateChangeListener
  - gmv_poiClickListener
  - gmv_snapshotReadyCallback
```
- [Markers Bindings][Markers Bindings]:

```
  - gmv_markers
  - gmv_markerClickListener
  - gmv_infoWindowAdapter
  - gmv_infoWindowClickListener
  - gmv_infoWindowCloseListener
  - gmv_infoWindowLongClickListener
  - gmv_markerDragListener
```
- [Overlays Bindings][Overlays Bindings]:

```
  - gmv_groundOverlays
  - gmv_groundOverlayClickListener
```
- [Polygons Bindings][Polygons Bindings]:

```
  - gmv_polygons
  - gmv_polygonClickListener
```
- [Map Bindings][Map Bindings]:

```
  - gmv_polylines
  - gmv_polylineClickListener
```

## Getting Help
To report a specific problem or feature request, [open a new issue on Github][issue].

## Company
[Here](https://github.com/TangoAgency/) you can see open source work developed by Tango Agency.

Whether you're searching for a new partner or trusted team for creating your new great product we are always ready to start work with you.

You can contact us via contact@tango.agency.
Thanks in advance.

[Android Data Binding]: <https://developer.android.com/topic/libraries/data-binding/index.html>
[Android Data Binding Setup]: <https://developer.android.com/topic/libraries/data-binding/index.html#build_environment>
[issue]: <https://github.com/TangoAgency/Viking/issues/new>
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
