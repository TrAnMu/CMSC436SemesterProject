package groupproject.cmsc436.flow;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import groupproject.cmsc436.flow.Service.DatabaseService;

/**
 * Created by Travis on 5/6/17.
 */


public class EventFragment extends Fragment implements OnMapReadyCallback {
    public static final int PERMISSIONS_REQUEST_LOCATION = 1;
    private static final String BUNDLE_ASSEMBLED = "BUNDLE_ASSEMBLED";
    DatabaseService service;
    ImageView photoView;
    TextView eventText, broadcasterText, detailsText;
    SupportMapFragment mapFragment;
    GoogleMap googleMap;
    Event event;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        service = DatabaseService.getDBService();
        Bundle bundle = getArguments();
        String eventId = bundle.getString(BUNDLE_ASSEMBLED);
        event = service.getEvent(eventId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_event, container, false);


        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        photoView = (ImageView)view.findViewById(R.id.event_photo);
        eventText = (TextView)view.findViewById(R.id.event_title);
        broadcasterText = (TextView)view.findViewById(R.id.event_broadcaster);
        detailsText = (TextView)view.findViewById(R.id.event_details);

        eventText.setText(event.getEventName());
        broadcasterText.setText(event.getHostName());
        detailsText.setText("Created on: " +  event.getCreationTime() + " - " + event.getDescription());

        //TODO set photo once we have it...

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng latLng = new LatLng(event.getLatitude(), event.getLongtitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(event.getEventName());
        googleMap.addMarker(markerOptions);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11));
        this.googleMap = googleMap;
    }

    public static EventFragment newInstance(String eventId) {
        Bundle bundle = new Bundle();
        EventFragment fragment = new EventFragment();

        bundle.putString(BUNDLE_ASSEMBLED, eventId);
        fragment.setArguments(bundle);

        return fragment;
    }
}
