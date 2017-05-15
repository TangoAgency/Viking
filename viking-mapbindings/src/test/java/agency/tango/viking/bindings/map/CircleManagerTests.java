package agency.tango.viking.bindings.map;

import android.os.RemoteException;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.model.CircleOptions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import agency.tango.viking.bindings.map.managers.CircleManager;
import agency.tango.viking.bindings.map.models.BindableCircle;

@RunWith(MockitoJUnitRunner.class)
public class CircleManagerTests {

  //@Mock
  GoogleMap googleMap;

  @Mock
  IGoogleMapDelegate googleMapDelegate;

  @Test
  public void test() throws RemoteException {

    googleMap = getGoogleMap(googleMapDelegate);

    //given(googleMapDelegate.addCircle(any(CircleOptions.class))).willReturn();

    CircleManager<TestModel> circleManager = new CircleManager<TestModel>(onMapReadyCallback -> {
      onMapReadyCallback.onMapReady(googleMap);
    });

    //circleManager.add(googleMap, new TestModel(new CircleOptions()));
  }

  private static class TestModel extends BindableCircle {
    public TestModel(CircleOptions circleOptions) {
      super(circleOptions);
    }
  }

  private static GoogleMap getGoogleMap(IGoogleMapDelegate delegate) {
    Constructor c = null;
    try {
      c = GoogleMap.class.getDeclaredConstructor(IGoogleMapDelegate.class);
      c.setAccessible(true); // solution
      return (GoogleMap) c.newInstance(delegate);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }

    return null;
  }

}
