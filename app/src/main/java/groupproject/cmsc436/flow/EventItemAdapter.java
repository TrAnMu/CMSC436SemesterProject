package groupproject.cmsc436.flow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Catherine on 2017-04-28.
 */

public class EventItemAdapter extends ArrayAdapter<EventItem> {
    public EventItemAdapter(Context context, ArrayList<EventItem> events) {
        super(context, 0, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        EventItem event = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_list_item, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.eventName);
        TextView tvInfo = (TextView) convertView.findViewById(R.id.eventInfo);

        tvName.setText(event.eventName);
        tvInfo.setText(event.eventInfo);

        return convertView;
    }
}
