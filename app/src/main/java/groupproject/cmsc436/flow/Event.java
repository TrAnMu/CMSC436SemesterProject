package groupproject.cmsc436.flow;

/**
 * Created by Junze on 4/23/2017.
 */

public class Event {
    private String name = "";
    private double longtitude = 0;
    private double latitude = 0;
    private String hostname = "";
    public Event(String eventname, double longti, double lat, String host) {
        name = eventname;
        longtitude = longti;
        latitude = lat;
        hostname = host;
    }

    public void setEventName (String evtname) {
        name = evtname;
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
}
