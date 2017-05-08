package groupproject.cmsc436.flow;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import groupproject.cmsc436.flow.Service.DatabaseService;

import static android.app.Activity.RESULT_OK;


/**
 * Created by magneta94 on 5/4/17.
 */


public class CreateEventFragment extends Fragment {

    private Button createButton;
    private Button cancelButton;
    private Button setTimeButton;
    private EditText eventName;
    private EditText eventHost;
    private EditText eventLocation;
    private EditText eventEnd;
    private EditText eventDescription;
    private ImageButton cameraButton;
    private ImageView imageView;
    private String imageURI;
    private File file;
    private static final int REQUEST_IMAGE_CAPTURE = 9;


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
        eventLocation = (EditText) view.findViewById(R.id.event_location);

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
                newFragment.show(getActivity().getFragmentManager(),"Time Picker");

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
                String description  = eventDescription.getText().toString();
                String endTime  = TimePickerFragment.getTime();
                double lat = 0;
                double longi = 0;


                Geocoder geocoder = new Geocoder(getActivity().getApplicationContext()
                );
                List<Address> addresses = null;
                try {
                    while(addresses == null) {

                        addresses = geocoder.getFromLocationName(location, 1);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (addresses.size() > 0) {
                    lat = addresses.get(0).getLatitude();
                    longi = addresses.get(0).getLongitude();
                }


                Event event = new Event(name, longi, lat, host, endTime,imageURI,description);
                event.setCreationTime();
                DatabaseService.getDBService(getActivity().getApplicationContext()).addEvent(event);
                DatabaseService.getDBService(getActivity().getApplicationContext()).addEventPhoto(event);
                getActivity().finish();
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

            if(file != null) {
                Bitmap bitmapIn = BitmapFactory.decodeFile(file.getPath());
                imageView.setImageBitmap(bitmapIn);
                Uri uri = Uri.fromFile(file);
                imageURI = uri.toString();



            }
        }
    }



    public void onButtonClicked(View v){

    }

}
