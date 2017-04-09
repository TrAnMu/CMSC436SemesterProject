package groupproject.cmsc436.flow.Service;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import groupproject.cmsc436.flow.UserInfo;

/**
 * Created by Junze on 4/8/2017.
 */

public class DatabaseService {
    final static private String PATH = "https://cmsc436-6cb24.firebaseio.com/";
    private static final DatabaseService instance = new DatabaseService();

    private static DatabaseReference databaseReference;
    private static Map<String, UserInfo> users;

    public static DatabaseService getDBService(Context context) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReferenceFromUrl(PATH);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                GenericTypeIndicator<HashMap<String, UserInfo>> t = new GenericTypeIndicator<HashMap<String, UserInfo>>() {};
                users = dataSnapshot.getValue(t);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("TAG", "Failed to read value.", error.toException());
            }
        });
        return instance;
    }
    public void signUp(String user, String pass) {
        UserInfo userInfo = new UserInfo(user, pass);
        databaseReference.child(userInfo.getUsername()).setValue(userInfo);
    }
}
