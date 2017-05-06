package groupproject.cmsc436.flow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import groupproject.cmsc436.flow.Service.DatabaseService;

/**
 * Created by Junze on 4/8/2017.
 */

public class ProfileFragment extends Fragment {
    private ImageView profilePicture;
    private ImageButton galleryButton;
    private ImageButton cameraButton;

    private TextView nameTextView;
    private TextView likesTextView;

    private Button logOutButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profilePicture = (ImageView)view.findViewById(R.id.profile_image);
        galleryButton = (ImageButton)view.findViewById(R.id.profile_gallery);
        cameraButton = (ImageButton)view.findViewById(R.id.profile_camera);

        nameTextView = (TextView)view.findViewById(R.id.profile_name);
        likesTextView = (TextView)view.findViewById(R.id.profile_likes);


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




    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }
}
