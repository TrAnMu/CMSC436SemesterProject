package groupproject.cmsc436.flow;

/**
 * Created by Junze on 4/8/2017.
 */

public class UserInfo {
    private String userKey;
    private String username;
    private boolean defaulPicture = true;
    private String firstName;
    private String lastName;
    private long likesReceived = 0;

    public UserInfo(String key, String user, String first, String last) {
        userKey = key;
        username = user;
        firstName = first;
        lastName = last;
        likesReceived = 0;
    }

    public UserInfo(String key, String user, String first, String last, boolean isDefault, long likes) {
        userKey = key;
        username = user;
        firstName = first;
        lastName = last;
        defaulPicture = isDefault;
        likesReceived = likes;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void addLikesReceived() {
        likesReceived++;
    }

    public long getLikesReceived() {
        return likesReceived;
    }

    public String getUserId() {
        return userKey;
    }

    public void setDefaulPicture(boolean isDefault) {
        defaulPicture = isDefault;
    }

    public boolean getDefaulPicture() {
        return defaulPicture;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
    @Override
    public String toString() {
        return "username=" + username +
                ", first name=" + firstName +
                ", last name=" + lastName +
                ", isDefaultPic =" + defaulPicture +
                ", Likes Received=" + likesReceived;
    }
}
