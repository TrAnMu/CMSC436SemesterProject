package groupproject.cmsc436.flow;

import android.support.v4.app.Fragment;

public class RegistrationActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return RegistrationFragment.newInstance();
    }
}
