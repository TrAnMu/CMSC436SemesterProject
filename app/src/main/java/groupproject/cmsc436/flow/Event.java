package groupproject.cmsc436.flow;

/**
 * Created by Junze on 4/23/2017.
 */

public class Event {
    private String eventID;
    private String name = "";
    private double longtitude = 0;
    private double latitude = 0;
    private String hostname = "";
    private int likes = 0;
    public Event(String eventname, double longti, double lat, String host) {
        name = eventname;
        longtitude = longti;
        latitude = lat;
        hostname = host;
        likes = 0;
    }

    public void setEventID(String id) {
        eventID = (eventID == null)? id : eventID;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventName (String eventname) {
        name = eventname;
    }

    public String getEventName() {
        return name;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getHostName() {
        return hostname;
    }

    public void addLikes() {
        likes++;
    }

    public int getLikes() {
        return likes;
    }
}
