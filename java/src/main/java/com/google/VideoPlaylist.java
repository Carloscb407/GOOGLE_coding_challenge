package com.google;

import java.util.ArrayList;

/** A class used to represent a Playlist */
class VideoPlaylist {
    ArrayList<Video> video_list = new ArrayList<>();

    public ArrayList<Video> getVideo_list() {
        return video_list;
    }

    public void setVideo_list(ArrayList<Video> video_list) {
        this.video_list = video_list;
    }


}
