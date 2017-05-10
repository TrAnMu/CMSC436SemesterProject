package groupproject.cmsc436.flow;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

/**
 * Created by magneta94 on 4/18/17.
 */

public class SettingsActivity extends AppCompatActivity {

    private ToggleButton togglebutton ;
    private String myPref = "mypref";
    private String startPage;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startsettings);

        final SharedPreferences sharedPreferences = getSharedPreferences(myPref, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        togglebutton = (ToggleButton) findViewById(R.id.toggle_setting);

        togglebutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                 startPage = "map";
                }

                else {
                        startPage = "explore";
                    }

                editor.putString("startPage",startPage);
                editor.commit();

            }
        });

    }
}
