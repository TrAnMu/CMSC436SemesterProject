package groupproject.cmsc436.flow;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import groupproject.cmsc436.flow.Service.DatabaseService;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Junze on 4/8/2017.
 */

public class ProfileFragment extends Fragment {
    final private int GALLERY_REQUEST_CODE = 0;
    final private int CAMERA_REQUEST_CODE = 1;
    private static final String AUTHORITY = BuildConfig.APPLICATION_ID+".provider";

    private ImageView profilePictureImageView;
    private ImageButton galleryButton;
    private ImageButton cameraButton;

    private TextView nameTextView;
    private TextView likesTextView;

    private Button logOutButton;

    private UserInfo currentUser;

    private DatabaseService service;
    private File savedFile;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = DatabaseService.getDBService();
        currentUser = service.getCurrentUser();


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profilePictureImageView = (ImageView)view.findViewById(R.id.profile_image);
        galleryButton = (ImageButton)view.findViewById(R.id.profile_gallery);
        cameraButton = (ImageButton)view.findViewById(R.id.profile_camera);

        nameTextView = (TextView)view.findViewById(R.id.profile_name);
        likesTextView = (TextView)view.findViewById(R.id.profile_likes);

        if (currentUser.getDefaulPicture()) {
            profilePictureImageView.setImageResource(R.drawable.place_holder);
        } else {
            Glide.with(getActivity().getApplicationContext())
                    .using(new FirebaseImageLoader())
                    .load(service.getUserPhotoReference(currentUser.getUserId()+".jpg"))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(profilePictureImageView);
        }

        nameTextView.setText(currentUser.getFullName());
        likesTextView.setText(String.format(getResources().getString(R.string.profile_like_received), currentUser.getLikesReceived()));

        galleryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), GALLERY_REQUEST_CODE);
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent();
                cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    try {
                        savedFile = getPhotoFile();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }


                    if (savedFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(getActivity().getApplicationContext(), AUTHORITY, savedFile);

                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                    }
                } else {
                    cameraButton.setEnabled(false);
                }
            }
        });

        logOutButton = (Button) view.findViewById(R.id.profile_log_out_button);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseService.getDBService().signOut();
                Intent registerIntent = new Intent(getActivity().getApplicationContext(), LogInActivity.class);
                startActivity(registerIntent);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri uri = null;
            if (requestCode == GALLERY_REQUEST_CODE && data != null && data.getData() != null) {
                try {
                    uri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    profilePictureImageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == CAMERA_REQUEST_CODE) {
                try {
                    if (savedFile != null) {
                        uri = Uri.fromFile(savedFile);
                        Bitmap bitmap = BitmapFactory.decodeFile(savedFile.getPath());
                        profilePictureImageView.setImageBitmap(bitmap);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (uri != null) {
                service.setCurrentUserPic(currentUser.getUserId(), uri);
                currentUser.setDefaulPicture(false);
            }
        }
    }

    private File getPhotoFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }



    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }
}
