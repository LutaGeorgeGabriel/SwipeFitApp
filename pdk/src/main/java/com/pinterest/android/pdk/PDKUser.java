package com.pinterest.android.pdk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PDKUser extends PDKModel {

    private String uid;
    private String username;
    private String firstName;
    private String lastName;
    private String bio;
    private Date createdAt;
    private String imageUrl;
    private Integer imageWidth;
    private Integer imageHeight;
    private Integer followersCount;
    private Integer followingCount;
    private Integer pinCount;
    private Integer likesCount;
    private Integer boardsCount;

    public static PDKUser makeUser(Object obj) {
        PDKUser user = new PDKUser();
        try {
            if (obj instanceof JSONObject) {

                JSONObject dataObj = (JSONObject)obj;
                if (dataObj.has("id")) {
                    user.setUid(dataObj.getString("id"));
                }
                if (dataObj.has("first_name")) {
                    user.setFirstName(dataObj.getString("first_name"));
                }
                if (dataObj.has("last_name")) {
                    user.setLastName(dataObj.getString("last_name"));
                }
                if (dataObj.has("username")) {
                    user.setUsername(dataObj.getString("username"));
                }
                if (dataObj.has("bio")) {
                    user.setBio(dataObj.getString("bio"));
                }
                if (dataObj.has("created_at")) {
                    user.setCreatedAt(
                        Utils.getDateFormatter().parse(dataObj.getString("created_at")));
                }
                if (dataObj.has("counts")) {
                    JSONObject countsObj = dataObj.getJSONObject("counts");
                    if (countsObj.has("pins")) {
                        user.setLikesCount(countsObj.getInt("pins"));
                    }
                    if (countsObj.has("following")) {
                        user.setFollowingCount(countsObj.getInt("following"));
                    }
                    if (countsObj.has("followers")) {
                        user.setFollowersCount(countsObj.getInt("followers"));
                    }
                    if (countsObj.has("boards")) {
                        user.setBoardsCount(countsObj.getInt("boards"));
                    }
                    if (countsObj.has("likes")) {
                        user.setLikesCount(countsObj.getInt("likes"));
                    }
                }
                if (dataObj.has("image")) {
                    JSONObject imageObj = dataObj.getJSONObject("image");
                    Iterator<String> keys = imageObj.keys();

                    //TODO: for now we'll have just one image map. We will change this logic after appathon
                    while(keys.hasNext()) {
                        String key = keys.next();
                        if (imageObj.get(key) instanceof JSONObject) {
                            JSONObject iObj = imageObj.getJSONObject(key);
                            if (iObj.has("url")) {
                                user.setImageUrl(iObj.getString("url"));
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            Utils.loge("PDK: PDKUser parse JSON error %s", e.getLocalizedMessage());
        } catch (ParseException e) {
            Utils.loge("PDK: PDKUser parse error %s", e.getLocalizedMessage());
        }
        return user;
    }

    public static List<PDKUser> makeUserList(Object obj) {
        List<PDKUser> userList = new ArrayList<PDKUser>();
        try {
            if (obj instanceof JSONArray) {

                JSONArray jAarray = (JSONArray)obj;
                int size = jAarray.length();
                for (int i = 0; i < size; i++) {
                    JSONObject dataObj = jAarray.getJSONObject(i);
                    userList.add(makeUser(dataObj));
                }
            }
        } catch (JSONException e) {
            Utils.loge("PDK: PDKUserList parse JSON error %s", e.getLocalizedMessage());
        }
        return userList;
    }

    @Override
    public String getUid() {

        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    public Integer getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }

    public Integer getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    public Integer getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(Integer followingCount) {
        this.followingCount = followingCount;
    }

    public Integer getPinCount() {
        return pinCount;
    }

    public void setPinCount(Integer pinCount) {
        this.pinCount = pinCount;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Integer getBoardsCount() {
        return boardsCount;
    }

    public void setBoardsCount(Integer boardsCount) {
        this.boardsCount = boardsCount;
    }
}
