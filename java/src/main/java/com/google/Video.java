package com.google;

import java.util.Collections;
import java.util.List;

/** A class used to represent a video. */
class Video {

  private final String title;
  private final String videoId;
  private final List<String> tags;
  private boolean flagged;
  private String flag_reason;

  public String getFlag_reason() {
    return "(reason: " + flag_reason + ")";
  }

  public void setFlag_reason(String flag_reason) {
    this.flag_reason = flag_reason;
  }

  public boolean isFlagged() {
    return flagged;
  }

  public void setFlagged(boolean flagged) {
    this.flagged = flagged;
  }

  String getFlag_info(){
    return title + " (reason: " + flag_reason + ")";
  }

  String getFull_info(){
    return getInfo() + " - FLAGGED " + getFlag_reason();
  }

  Video(String title, String videoId, List<String> tags) {
    this.title = title;
    this.videoId = videoId;
    this.tags = Collections.unmodifiableList(tags);
  }

  /** Returns the title of the video. */
  String getTitle() {
    return title;
  }

  /** Returns the video id of the video. */
  String getVideoId() {
    return videoId;
  }

  /** Returns a readonly collection of the tags of the video. */
  List<String> getTags() {
    return tags;
  }

  String getInfo() {
    String info = title + " (" + videoId + ") " + tags;
    return info.replaceAll(",", "");
  }
}
