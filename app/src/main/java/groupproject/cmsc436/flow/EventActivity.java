package groupproject.cmsc436.flow;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by Travis on 5/6/17.
 */

public class EventActivity extends SingleFragmentActivity {
    private static final String INTENT_CREATED = "INTENT_CREATED";

    protected Fragment createFragment() {
        Intent intent = getIntent();

        return EventFragment.newInstance(intent.getStringExtra(INTENT_CREATED));
    }

    public static Intent newIntent(Context context, String eventID) {
        Intent newIntent = new Intent(context, EventActivity.class);
        newIntent.putExtra(INTENT_CREATED, eventID);

        return newIntent;
    }
}
