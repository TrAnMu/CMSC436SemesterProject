package groupproject.cmsc436.flow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by Catherine on 2017-04-25.
 */

public class ExploreFragment extends android.support.v4.app.Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View view = inflater.inflate(R.layout.fragment_explore, container, false);


        Spinner fspinner = (Spinner) view.findViewById(R.id.filter_spinner);
        ArrayAdapter<CharSequence> fAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.filter_array, android.R.layout.simple_spinner_item);

        fAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fspinner.setAdapter(fAdapter);

        ArrayList<EventItem> eventsArray = new ArrayList<EventItem>();

        EventItemAdapter adapter = new EventItemAdapter(getContext(), eventsArray);

        ListView listView = (ListView) view.findViewById(R.id.event_list);
        listView.setAdapter(adapter);

        return view;
    }

    public static Fragment newInstance() {
        return new ExploreFragment();
    }
}
