package groupproject.cmsc436.flow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Junze on 4/8/2017.
 */

public class UserInfo {
    private String username;
    private String password;
    private String profile_pic = "@drawable/place_holder";
    private List<String> friends = new ArrayList<String>();
    private List<String> favLocation = new ArrayList<String>();
    private List<String> likedEvent = new ArrayList<String>();
    private List<String> travelledPlace = new ArrayList<String>();

    public UserInfo() {
    }

    public UserInfo(String user, String pass) {
        username = user;
        password = pass;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void addFriend(String user) {
        friends.add(user);
    }

    public void addFriend(List<String> users) {
        friends.addAll(users);
    }

    public List<String> getFavLocations() {
        return favLocation;
    }

    public void addFavLocation(String loc) {
        favLocation.add(loc);
    }

    public void addFavLocation(List<String> locs) {
        favLocation.addAll(locs);
    }

    public void setProfilePic(String img_address) {
        profile_pic = img_address;
    }

    public String getProfilePic() {
        return profile_pic;
    }

    public void addLikedEvent(String event) {
        if (!likedEvent.contains(event)) {
            likedEvent.add(event);
        }
    }

    public List<String> getLikedEvent() {
        return likedEvent;
    }

    public void addTravelledLocation(String loc) {
        if (!travelledPlace.contains(loc)) {
            travelledPlace.add(loc);
        }
    }

    public List<String> getTravelledLocation() {
        return travelledPlace;
    }

    @Override
    public String toString() {
        return "username=" + username +
                ", profilePic=" + profile_pic +
                ", friends=" + friends  +
                ", Fav Location=" + favLocation +
                ", Liked Events=" + likedEvent +
                ", Places Traveled=" + travelledPlace;
    }
}
