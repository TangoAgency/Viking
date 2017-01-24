package agency.tango.viking.bindings.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import agency.tango.viking.bindings.map.managers.CircleManager;
import agency.tango.viking.bindings.map.managers.IMapEntityManager;
import agency.tango.viking.bindings.map.models.BindableCircle;

import static org.mockito.Mockito.mock;

@RunWith(value = MockitoJUnitRunner.class)
public class MapEntityManagerBaseTests {

  @Mock
  IMapEntityManager.MapResolver mapResolver;

  @Mock
  OnMapReadyCallback onMapReadyCallback;

  CircleManager circleManager;

  @Before
  public void setup() {
  }

  @Test
  public void addItems() {
    GoogleMap googleMapMock = mock(GoogleMap.class);
    circleManager = new CircleManager(onMapReadyCallback -> {
    });

    List<BindableCircle> items = Arrays.asList(new BindableCircle(new CircleOptions()));
    circleManager.addItems(googleMapMock, items);
  }
}
