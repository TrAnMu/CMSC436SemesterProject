package groupproject.cmsc436.flow;

import android.support.v4.app.Fragment;

/**
 * A login screen that offers login via email/password.
 */
public class LogInActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return LogInFragment.newInstance();
    }
}

