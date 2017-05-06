package groupproject.cmsc436.flow;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by Junze on 4/23/2017.
 */

public class Event {


    private String eventName = "";
    private double longtitude = 0;
    private double latitude = 0;
    private String hostName = "";
    private String endTime = "";
    private Date creationTime;
    private Bitmap img;
    private int likes;


    public Event(){
        eventName =  "";
        longtitude = 0;
        latitude = 0;
        hostName = "";
        endTime = "6:00pm";
        img = null;


    }

    public Event(String eventname, double longti, double lat, String host,
                 String endTimeIn,Bitmap imgIn) {
        eventName = eventname;
        longtitude = longti;
        latitude = lat;
        hostName = host;
        img = imgIn;
        endTime = endTimeIn;



    }

    public void setEventName (String evtname) {
        eventName = evtname;
    }

    public String getEventName() {
        return eventName;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getHostName() {
        return hostName;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setCreationTime(){
        creationTime = new Date();
    }



}
