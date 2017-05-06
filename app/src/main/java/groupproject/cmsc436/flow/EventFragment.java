package groupproject.cmsc436.flow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.OnMapReadyCallback;

import groupproject.cmsc436.flow.Service.DatabaseService;

/**
 * Created by Travis on 5/6/17.
 */

public class EventFragment extends Fragment implements OnMapReadyCallback {
    private static final String BUNDLE_ASSEMBLED = "BUNDLE_ASSEMBLED";
    DatabaseService service;
    Event event;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        service = DatabaseService.getDBService(getActivity());
        Bundle bundle = getArguments();
        String eventId = bundle.getString(BUNDLE_ASSEMBLED);
        event = service.getEvent(eventId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        //Do some stuff.

        return view;
    }

    public static EventFragment newInstance(String eventId) {
        Bundle bundle = new Bundle();
        EventFragment fragment = new EventFragment();

        bundle.putString(BUNDLE_ASSEMBLED, eventId);
        fragment.setArguments(bundle);

        return fragment;
    }
}
