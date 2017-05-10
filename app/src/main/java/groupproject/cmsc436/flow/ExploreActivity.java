package groupproject.cmsc436.flow;

import android.support.v4.app.Fragment;

/**
 * Created by Catherine on 2017-04-25.
 */

public class ExploreActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return ExploreFragment.newInstance();
    }
}
