package groupproject.cmsc436.flow.Service;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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

    private static DatabaseReference databaseReference;
    private static Map<String, UserInfo> users;
    private static Map<String, Event> allEvents = new HashMap<>();
    private static StorageReference eventPhotoReference;

    private static String EVENT = "event";

    private DatabaseReference dbRef;


    public static DatabaseService getDBService(Context context) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        eventPhotoReference = storage.getReference().child(EVENT);

        databaseReference = database.getReferenceFromUrl(PATH);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                Map<String, HashMap<String, HashMap<String, Object>>> map = (Map<String, HashMap<String, HashMap<String, Object>>>) dataSnapshot.getValue();
                HashMap<String, HashMap<String, Object>> events = map.get(EVENT);
                for (Map.Entry<String, HashMap<String, Object>> entry: events.entrySet()) {
                    if (!allEvents.containsKey(entry.getKey())) {

                        HashMap eventValues = entry.getValue();

                        String key = entry.getKey();
                        String eventName=eventValues.get("eventName").toString();
                        Double longtitude=Double.parseDouble(eventValues.get("longtitude").toString());

                        Double lat=Double.parseDouble(eventValues.get("latitude").toString());

                        String host=eventValues.get("hostName").toString();

                        String end=eventValues.get("endTime").toString();

                        String id=eventValues.get("eventID").toString();
                        String creationTime=eventValues.get("creationTime").toString();
                        String imageUri=eventValues.get("imageUri").toString();
                        String likes=eventValues.get("likes").toString();
                        String description=eventValues.get("description").toString();

                        Event event=new Event(eventName,longtitude,lat,host,end,imageUri,description);

                        event.setEventID(id);
                        event.setLikes(likes);

                        event.setCreationTime(creationTime);

                        allEvents.put(key,event);
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
        return instance;
    }
    public void signUp(String email, String password) throws Exception {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
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

        dbRef = FirebaseDatabase.getInstance().getReference();
        String key =  dbRef.child(EVENT).push().getKey();
        event.setEventID(key);
        dbRef.child(EVENT).child(key).setValue(event);
    }


    public Event getEvent(String eventName) {
        return allEvents.get(eventName);
    }

    public HashMap getAllEvents(){
        return (HashMap) allEvents;
    }


    public void addEventPhoto(Event event) {


        StorageReference eventRef = eventPhotoReference.child(event.getEventID()+".jpg");

        Uri uri = Uri.parse(event.getImageUri());

        UploadTask uploadTask = eventRef.putFile(uri);
        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Log.d("StoreImage", "Success!");
            }
        });
    }


}