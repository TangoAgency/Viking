package agency.tango.viking.example;

import agency.tango.viking.annotations.AutoModule;
import agency.tango.viking.bindings.map.GoogleMapView;
import agency.tango.viking.example.databinding.ActivityMapBinding;
import agency.tango.viking.mvvm.ActivityView;
import android.content.Context;
import android.os.Bundle;

@AutoModule
public class MapActivity extends ActivityView<MapViewModel, ActivityMapBinding> {

    private GoogleMapView mapView;

    public MapActivity() {
        super(R.layout.activity_map);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mapView.onCreate(savedInstanceState);
    }

    @Override
    protected void inject(Context context) {
        App.get(context)
                .getActivityComponentBuilder(MapActivity.class, MapActivity_Component.Builder.class)
                .build()
                .injectMembers(this);
    }

    @Override
    protected void bind(ActivityMapBinding binding)
    {
        binding.setViewModel(viewModel());
        //binding.setView(this);
        mapView = binding.map;
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
//    {
//        View view = super.onCreateView(inflater, container, savedInstanceState);
//        Bundle mapViewBundle;
//        if (savedInstanceState != null)
//        {
//            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
//        }
//        else
//        {
//            mapViewBundle = new Bundle();
//        }
//        mapView.onCreate(mapViewBundle);
//        return view;
//    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
//        super.onSaveInstanceState(outState);
//        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
//        if (mapViewBundle == null)
//        {
//            mapViewBundle = new Bundle();
//            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
//        }
//
//        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause()
    {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy()
    {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}