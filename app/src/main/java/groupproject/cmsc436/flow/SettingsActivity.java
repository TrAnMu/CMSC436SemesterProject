package groupproject.cmsc436.flow;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by magneta94 on 4/18/17.
 */

public class SettingsActivity extends AppCompatActivity {
    ArrayList<DataModel> dataModels;
    ListView listView;
    private static SettingsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        listView = (ListView)findViewById(R.id.set_list);

        dataModels= new ArrayList<>();

        dataModels.add(new DataModel("General Settings"));
        dataModels.add(new DataModel("Map Settings"));
        dataModels.add(new DataModel("Theme Settings "));


        adapter= new SettingsAdapter(dataModels,getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataModel dataModel= dataModels.get(position);

                Snackbar.make(view, dataModel.getName()+"\n", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });
    }
}
