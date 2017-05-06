package groupproject.cmsc436.flow;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

import groupproject.cmsc436.flow.Service.DatabaseService;

/**
 * Created by magneta94 on 5/4/17.
 */

public class CreateEventFragment extends Fragment{

    private Button createButton;
    private EditText eventName;
    private EditText eventHost;
    private EditText eventLocation;
    private EditText eventEnd;





    public static CreateEventFragment newInstance() {
        return new CreateEventFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_createvent, container, false);

        eventName = (EditText) view.findViewById(R.id.event_name);
        eventHost = (EditText) view.findViewById(R.id.event_host);
        eventLocation= (EditText) view.findViewById(R.id.event_location);
        eventEnd = (EditText) view.findViewById(R.id.event_end);

        createButton = (Button) view.findViewById(R.id.create_button);




        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

           String endTime = eventEnd.getText().toString();
           String name = eventName.getText().toString();
           String host = eventHost.getText().toString();
           String location = eventLocation.getText().toString();
           double lat = 0;
           double longi = 0;


                Geocoder geocoder = new Geocoder(getActivity().getApplicationContext()
                );
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocationName(location, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(addresses.size() > 0) {
                    lat = addresses.get(0).getLatitude();
                    longi = addresses.get(0).getLongitude();
                }


           Event event = new Event(name,longi,lat,host);

           DatabaseService.getDBService(getActivity().getApplicationContext()).addEvent(event);

            }
        });


        return view;
    }


    public LatLng getLocationFromAddress(String strAddress){

        Geocoder coder = new Geocoder(getActivity().getApplicationContext());
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address==null) {
                return null;
            }
            Address location=address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng (location.getLatitude() * 1E6,
                    (location.getLongitude() * 1E6));

            return p1;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return p1;
    }






}
