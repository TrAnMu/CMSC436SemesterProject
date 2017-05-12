package groupproject.cmsc436.flow.Service;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import groupproject.cmsc436.flow.Event;
import groupproject.cmsc436.flow.UserInfo;

/**
 * Created by Junze on 4/8/2017.
 */

public class DatabaseService {
    private static final DatabaseService instance = new DatabaseService();

    private DatabaseReference eventReference;
    private DatabaseReference userReference;

    private StorageReference eventPhotoReference;
    private StorageReference userProfileReference;

    private Map<String, UserInfo> users;
    private Map<String, Event> allEvents;

    private String EVENT = "event";
    private String USER ="user";

    private DatabaseService() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        eventReference = database.getReference().child(EVENT);
        eventPhotoReference = storage.getReference().child(EVENT);

        userReference = database.getReference().child(USER);
        userProfileReference = storage.getReference().child(USER);

        users = new HashMap<>();
        allEvents = new HashMap<>();
        eventReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, HashMap<String, Object>> events = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();
                if (events != null) {
                    for (Map.Entry<String, HashMap<String, Object>> entry : events.entrySet()) {
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
                    }
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
                if (userList != null) {
                    for (Map.Entry<String, HashMap<String, Object>> entry : userList.entrySet()) {
                        if (!users.containsKey(entry.getKey())) {
                            HashMap<String, Object> userValues = entry.getValue();
                            String userID = entry.getKey();
                            String userName = userValues.get("username").toString();
                            String firstName = userValues.get("firstName").toString();
                            String lastName = userValues.get("lastName").toString();
                            boolean isDefaultPic = Boolean.parseBoolean(userValues.get("defaulPicture").toString());
                            long likedReceived = Long.parseLong(userValues.get("likesReceived").toString());
                            UserInfo user = new UserInfo(userID, userName, firstName, lastName, isDefaultPic, likedReceived);
                            users.put(userID, user);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("TAG", "Failed to read value.", error.toException());
            }
        });
    }


    public static DatabaseService getDBService () {
        return instance;
    }
    public void signUp(final String email, final String password, final String first, final String last, final Context context) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            String key = task.getResult().getUser().getUid();
                            UserInfo user = new UserInfo(key, email, first, last);
                            userReference.child(key).setValue(user);
                        }
                    }
                });

    }

    public void signIn(String email, String password, final Context context) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public void addEvent(Event event) {
        String key = eventReference.push().getKey();
        event.setEventID(key);
        eventReference.child(key).setValue(event);
    }

    public Event getEvent(String eventID) {
        return allEvents.get(eventID);
    }
    public UserInfo getCurrentUser() {
        return users.get(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    public void setCurrentUserPic(final String userID, Uri photoFileUri) {
        StorageReference profileRef = userProfileReference.child(userID+".jpg");
        UploadTask uploadTask = profileRef.putFile(photoFileUri);
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
                userReference.child(userID).child("defaulPicture").setValue(false);
                users.get(userID).setDefaulPicture(false);
                Log.d("StoreImage", "Success!");
            }
        });
    }

    public StorageReference getUserPhotoReference(String userIdjpg) {
        return userProfileReference.child(userIdjpg);
    }

    public StorageReference getEventPhotoReference(String eventIdjpg) {
        return eventPhotoReference.child(eventIdjpg);
    }

    public List<Event> getAllEvents() {
        List<Event> list = new ArrayList<Event>(allEvents.values());
        return list;
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