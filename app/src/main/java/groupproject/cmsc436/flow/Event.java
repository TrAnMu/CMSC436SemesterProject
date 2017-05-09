package groupproject.cmsc436.flow;

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
    private String creationTime;
    private String eventID;
    private String imageURI;
    private int likes;
    private String description;


    public Event(){
        eventName =  "";
        longtitude = 0;
        latitude = 0;
        hostName = "";
        endTime = "6:00pm";
        description = "";
        likes = 0;
        imageURI = "";


    }

    public Event(String eventname, double longti, double lat, String host,
                 String endTimeIn,String imgIn,String descr) {
        eventName = eventname;
        longtitude = longti;
        latitude = lat;
        hostName = host;
        imageURI = imgIn;
        likes = 0;
        endTime = endTimeIn;
        description = descr;



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

    public String getDescription(){
        return description;
    }

    public int getLikes(){
        return likes;
    }

    public void setCreationTime(){
        creationTime = new Date().toString();
    }


    public String getEventID() {
             return eventID;
          }

    public void setEventID(String ID)  {
        this.eventID = ID;
    }


    public String getImageUri(){
      return imageURI;

    }


    public void setCreationTime(String time){
        creationTime = time;
    }

    public String getCreationTime(){
        return creationTime;
    }

    public void setLikes(String likes) {
        this.likes =Integer.parseInt(likes);
    }

    public void addLikes() {
        likes++;
    }


}
