package groupproject.cmsc436.flow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Junze on 4/8/2017.
 */

public class ProfileFragment extends Fragment {
    private TextView usernameTextView;
    private TextView likesTextView;
    private TextView placesTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        usernameTextView = (TextView)view.findViewById(R.id.profile_username);
        likesTextView = (TextView)view.findViewById(R.id.profile_likes);
        placesTextView = (TextView)view.findViewById(R.id.profile_places_travel);

        return view;
    }




    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }
}
