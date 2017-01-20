package net.droidlabs.vikingmap.views;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;

import agency.tango.viking.mvvm.FragmentView;
import agency.tango.viking.mvvm.ViewModel;

public abstract class MapAwareFragmentView<VM extends ViewModel, VD extends ViewDataBinding>
    extends FragmentView<VM, VD> {
  private static final String MAPVIEW_BUNDLE_KEY = "mapview_bundle_key";

  public MapAwareFragmentView(@LayoutRes int layoutIdRes) {
    super(layoutIdRes);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = super.onCreateView(inflater, container, savedInstanceState);

    MapsInitializer.initialize(getActivity());

    Bundle mapViewBundle;
    if (savedInstanceState != null) {
      mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
    } else {
      mapViewBundle = new Bundle();
    }
    mapView().onCreate(mapViewBundle);
    return view;
  }

  public abstract MapView mapView();

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
    if (mapViewBundle == null) {
      mapViewBundle = new Bundle();
      outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
    }

    mapView().onSaveInstanceState(mapViewBundle);
  }

  @Override
  public void onResume() {
    super.onResume();
    mapView().onResume();
  }

  @Override
  public void onStart() {
    super.onStart();
    mapView().onStart();
  }

  @Override
  public void onStop() {
    super.onStop();
    mapView().onStop();
  }

  @Override
  public void onPause() {
    mapView().onPause();
    super.onPause();
  }

  @Override
  public void onDestroy() {
    mapView().onDestroy();
    super.onDestroy();
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    mapView().onLowMemory();
  }
}
