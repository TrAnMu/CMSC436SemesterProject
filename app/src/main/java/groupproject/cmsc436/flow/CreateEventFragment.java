package groupproject.cmsc436.flow;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.IOException;
import java.util.List;

import groupproject.cmsc436.flow.Service.DatabaseService;

import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.location.LocationServices.FusedLocationApi;
import static groupproject.cmsc436.flow.MapActivity.MY_PERMISSIONS_REQUEST_LOCATION;


/**
 * Created by magneta94 on 5/4/17.
 */


public class CreateEventFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private static final long LOCATION_REFRESH_TIME = 0;
    private static final float LOCATION_REFRESH_DISTANCE = 0;
    private Button createButton;
    private Button cancelButton;
    private Button setTimeButton;
    private EditText eventName;
    private EditText eventHost;
    private TextView eventLocation;
    private EditText eventEnd;
    private EditText eventDescription;
    private ImageButton cameraButton;
    private ImageView imageView;
    private String imageURI;
    private File file;
    private Location mLastLocation;
    double lat = 0;
    double longi = 0;

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_IMAGE_CAPTURE = 9;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }


    }

    public static CreateEventFragment newInstance() {
        return new CreateEventFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_createvent, container, false);


        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        eventName = (EditText) view.findViewById(R.id.event_name);
        eventHost = (EditText) view.findViewById(R.id.event_host);
        eventLocation = (TextView) view.findViewById(R.id.event_location);

        eventDescription = (EditText) view.findViewById(R.id.description);


        imageView = (ImageView) view.findViewById(R.id.image_view);

        setTimeButton = (Button) view.findViewById(R.id.event_end_btn);
        createButton = (Button) view.findViewById(R.id.create_button);
        cancelButton = (Button) view.findViewById(R.id.cancel_button);
        cameraButton = (ImageButton) view.findViewById(R.id.imageButton);


        cameraButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {

                    file = getPhotoFile();

                    if (file == null) {
                        cameraButton.setEnabled(false);
                    }


                    Uri photoURI = Uri.fromFile(file);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);


                } else {
                    cameraButton.setEnabled(false);
                }


            }

        });

        setTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getActivity().getFragmentManager(), "Time Picker");

            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().finish();

            }
        });


        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = eventName.getText().toString();
                String host = eventHost.getText().toString();
                String location = eventLocation.getText().toString();
                String description = eventDescription.getText().toString();
                String endTime = TimePickerFragment.getTime();

                Geocoder geocoder = new Geocoder(getActivity().getApplicationContext()
                );
                List<Address> addresses = null;
                try {
                    while (addresses == null) {

                        addresses = geocoder.getFromLocationName(location, 1);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (addresses.size() > 0) {
                    lat = addresses.get(0).getLatitude();
                    longi = addresses.get(0).getLongitude();
                }


                if (mLastLocation != null) {
                    lat = mLastLocation.getLatitude();
                    longi = mLastLocation.getLongitude();
                }


                if (name.equals("") || host.equals("") || imageURI.equals("") ||
                    description.equals("") || endTime.equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.none_fields_blank, Toast.LENGTH_SHORT).show();
                } else {
                    Event event = new Event(name, longi, lat, host, endTime, imageURI, description);
                    event.setCreationTime();
                    DatabaseService.getDBService().addEvent(event);
                    DatabaseService.getDBService().addEventPhoto(event);
                    getActivity().finish();

                }
            }
        });


        return view;
    }


    private File getPhotoFile() {
        File externalPhotoDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalPhotoDir == null) {
            return null;
        }

        return new File(externalPhotoDir, "IMG" + System.currentTimeMillis() + ".jpg");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            if (file != null) {
                Bitmap bitmapIn = BitmapFactory.decodeFile(file.getPath());
                imageView.setImageBitmap(bitmapIn);
                Uri uri = Uri.fromFile(file);
                imageURI = uri.toString();


            }
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {



        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionCheck();
            return;
        }

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,this);

           mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            lat = mLastLocation.getLatitude();
            longi = mLastLocation.getLongitude();
            eventLocation.setText(Double.toString(lat) + " " + Double.toString(longi));

        }





    }




private void permissionCheck() {
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(getActivity().getApplicationContext())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }





    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;

    }



}
