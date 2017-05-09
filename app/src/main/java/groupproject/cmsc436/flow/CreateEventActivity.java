package groupproject.cmsc436.flow;

import android.support.v4.app.Fragment;

/**
 * Created by magneta94 on 5/4/17.
 */
public class CreateEventActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return CreateEventFragment.newInstance();
    }




}
