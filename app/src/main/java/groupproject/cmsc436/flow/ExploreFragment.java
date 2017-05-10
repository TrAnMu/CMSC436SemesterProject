package groupproject.cmsc436.flow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import groupproject.cmsc436.flow.Service.DatabaseService;

/**
 * Created by Catherine on 2017-04-25.
 */

public class ExploreFragment extends android.support.v4.app.Fragment {
    String currlist = "";
    private EventAdapter eventAdapter;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View view = inflater.inflate(R.layout.fragment_explore, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.event_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        currlist = this.getString(R.string.event_list);

        updateUI();

        return view;
    }

    public static Fragment newInstance() {
        return new ExploreFragment();
    }

    private class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Event event;

        TextView eventText, infoText, distanceText;
        ImageView photoView;

        public EventHolder(View view) {
            super(view);

            view.setOnClickListener(this);

            eventText = (TextView) view.findViewById(R.id.list_item_event_title);
            infoText = (TextView) view.findViewById(R.id.list_item_event_info);
            distanceText = (TextView) view.findViewById(R.id.list_item_event_distance);
            photoView = (ImageView) view.findViewById(R.id.list_item_event_photo);
        }

        public void bindEvent(Event eventIn) {
            event = eventIn;

            eventText.setText(event.getEventName());
            infoText.setText(event.getHostName());
        }

        @Override
        public void onClick(View view) {
            Intent intent = EventActivity.newIntent(getActivity(), event.getEventID());
            startActivity(intent);
        }
    }

    private class EventAdapter extends RecyclerView.Adapter<EventHolder> {
        private List<Event> eventList;

        public EventAdapter (List<Event> listIn) {
            eventList = listIn;
        }

        public void setEvents(List<Event> listIn) {
            eventList = listIn;
        }

        @Override
        public EventHolder onCreateViewHolder(ViewGroup vg, int integerIn) {
            LayoutInflater layIn = LayoutInflater.from(getActivity());

            View view = layIn.inflate(R.layout.event_list_item, vg, false);

            return new EventHolder(view);

        }

        @Override
        public void onBindViewHolder(EventHolder sh, int integerIn) {
            Event posit = eventList.get(integerIn);

            sh.bindEvent(posit);
        }

        public int getItemCount() {
            Log.d("abcd", Integer.toString(eventList.size()));
            return eventList.size();

        }
    }

    private void updateUI() {
        List<Event> es = DatabaseService.getDBService().getAllEvents();

        if(eventAdapter == null) {
            eventAdapter = new EventAdapter(es);

            recyclerView.setAdapter(eventAdapter);
        }
        else {
            eventAdapter.setEvents(es);
            eventAdapter.notifyDataSetChanged();
        }
    }
}
