package groupproject.cmsc436.flow.Service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import groupproject.cmsc436.flow.Event;
import groupproject.cmsc436.flow.UserInfo;

/**
 * Created by Junze on 4/8/2017.
 */

public class DatabaseService {
    final static private String PATH = "https://cmsc436-6cb24.firebaseio.com/";
    private static final DatabaseService instance = new DatabaseService();

    private static DatabaseReference eventReference;
    private static DatabaseReference userReference;
    private static Map<String, UserInfo> users;
    private static Map<String, Event> allEvents = new HashMap<>();

    private static String EVENT = "event";
    private static String USER ="user";


    public static DatabaseService getDBService(Context context) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        eventReference = database.getReference().child(EVENT);
        userReference = database.getReference().child(USER);
//        eventReference = database.getReferenceFromUrl(PATH).child(EVENT);
//        userReference = database.getReferenceFromUrl(PATH).child(USER);
        eventReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                HashMap<String, HashMap<String, Object>> events = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();
                for (Map.Entry<String, HashMap<String, Object>> entry: events.entrySet()) {
                    if (!allEvents.containsKey(entry.getKey())) {
                        HashMap<String, Object> eventValues = entry.getValue();
                        String eventName = eventValues.get("eventName").toString();
                        Double longtitude = Double.parseDouble(eventValues.get("longtitude").toString());
                        Double lat = Double.parseDouble(eventValues.get("latitude").toString());
                        String host = eventValues.get("hostName").toString();
                        Event event = new Event(eventName, longtitude, lat, host);
                        allEvents.put(eventName, event);
                    }
                    Log.d("events", allEvents.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("TAG", "Failed to read value.", error.toException());
            }
        });

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                HashMap<String, HashMap<String, Object>> userList = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();
                for (Map.Entry<String, HashMap<String, Object>> entry: userList.entrySet()) {
                    if (!users.containsKey(entry.getKey())) {
                        HashMap<String, Object> userValues = entry.getValue();
                        String userID = entry.getKey();
                        String userName = userValues.get("username").toString();
                        String firstName = userValues.get("firstName").toString();
                        String lastName = userValues.get("lastName").toString();
                        long likedReceived = Long.parseLong(userValues.get("likesReceived").toString());
                        UserInfo user = new UserInfo(userID, userName, firstName, lastName, likedReceived);
                        users.put(userID, user);
                    }
                    Log.d("users", users.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("TAG", "Failed to read value.", error.toException());
            }
        });
        return instance;
    }
    public void signUp(final String email, final String password, final String first, final String last) throws Exception {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.d("EXCEPTION", task.toString());
                            throw new IllegalArgumentException();
                        }
                        String key = userReference.push().getKey();
                        UserInfo user = new UserInfo(key, email, first, last);
                        userReference.child(key).setValue(user);
                    }
                });
    }

    public void signIn(String email, String password) throws Exception{
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            throw new IllegalArgumentException();
                        }

                        // ...
                    }
                });
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public void addEvent(Event event) {
        eventReference.child(event.getEventName()).setValue(event);
    }

    public Event getEvent(String eventName) {
        return allEvents.get(eventName);
    }
}
