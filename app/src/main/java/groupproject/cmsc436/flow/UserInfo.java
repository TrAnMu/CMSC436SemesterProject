package groupproject.cmsc436.flow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Junze on 4/8/2017.
 */

public class UserInfo {
    private String username;
    private List<String> friends;
    private List<String> favLocation;
    private String profile_pic = "@drawable/place_holder";
    private List<String> likedEvent = null;
    private List<String> travelledPlace = null;

    public UserInfo(String user) {
        username = user;
        friends = null;
        favLocation = null;
    }

    public UserInfo(String user, List<String> friend, List<String> favLoc) {
        username = user;
        friends = friend;
        favLocation = favLoc;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void addFriend(String user) {
        if (friends != null) {
            friends.add(user);
        } else {
            friends = new ArrayList<String>();
            friends.add(user);
        }
    }

    public void addFriend(List<String> users) {
        if (friends != null) {
            friends.addAll(users);
        } else {
            friends = users;
        }
    }

    public List<String> getFavLocations() {
        return favLocation;
    }

    public void addFavLocation(String loc) {
        if (favLocation != null) {
            favLocation.add(loc);
        } else {
            favLocation = new ArrayList<String>();
            favLocation.add(loc);
        }
    }

    public void addFavLocation(List<String> locs) {
        if (favLocation != null) {
            favLocation.addAll(locs);
        } else {
            favLocation = locs;
        }
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
}
