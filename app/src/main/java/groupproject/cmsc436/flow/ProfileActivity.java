package groupproject.cmsc436.flow;

import android.support.v4.app.Fragment;

public class ProfileActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return ProfileFragment.newInstance();
    }
}
