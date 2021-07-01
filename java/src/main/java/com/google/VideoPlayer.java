package com.google;

import java.util.*;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private boolean video_in_play;
  private String video_playing_title;
  private String video_playing_id;
  private boolean paused;
  private ArrayList<String> playlistsName_actual = new ArrayList<>();
  private HashMap<String,VideoPlaylist> playlists_map = new HashMap<>();

  public boolean isPaused() {
    return paused;
  }

  public void setPaused(boolean paused) {
    this.paused = paused;
  }

  public void setVideo_in_play(boolean video_in_play) {
    this.video_in_play = video_in_play;
  }

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    System.out.println("Here's a list of all available videos:");
    String[] title_array = new String[videoLibrary.getVideos().size()];
    int i =0;
    for(Video video : videoLibrary.getVideos()){
      if(video.isFlagged()){
        title_array[i] = video.getFull_info();
      } else{
        title_array[i] = video.getInfo();
      }
      i++;
    }
    Arrays.sort(title_array, String.CASE_INSENSITIVE_ORDER);

    for (String s : title_array) {
      System.out.println(s);
    }
  }

  public void playVideo(String videoId) {
    Video video = videoLibrary.getVideo(videoId);
    if(video != null){
      if(!video.isFlagged()) {
        if (!video_in_play) {
          System.out.println("Playing video: " + video.getTitle());
          setVideo_in_play(true);
          video_playing_title = video.getTitle();
          video_playing_id = videoId;
        } else {
          System.out.println("Stopping video: " + video_playing_title);
          video_playing_title = video.getTitle();
          video_playing_id = videoId;
          setPaused(false);
          System.out.println("Playing video: " + video_playing_title);
        }
      } else {
        System.out.println("Cannot play video: Video is currently flagged " + video.getFlag_reason());
      }
    } else{
      System.out.println("Cannot play video: Video does not exist");
    }
  }

  public void stopVideo() {
    if(!video_in_play){
      System.out.println("Cannot stop video: No video is currently playing");
    } else{
      System.out.println("Stopping video: " + video_playing_title);
      video_playing_title = null;
      video_playing_id = null;
      setVideo_in_play(false);
      setPaused(false);
    }
  }

  public void playRandomVideo() {
    Random rand = new Random();
    List<Video> video_list = videoLibrary.getVideos();
    for(Video video : videoLibrary.getVideos()){
      if(video.isFlagged()){
        video_list.remove(video);
      }
    }
    if(video_list.size() >0) {
      if (!video_in_play) {
        video_playing_id = video_list.get(rand.nextInt(video_list.size())).getVideoId();
        video_playing_title = videoLibrary.getVideo(video_playing_id).getTitle();
        System.out.println("Playing video: " + video_playing_title);
        setVideo_in_play(true);
      } else {
        System.out.println("Stopping video: " + video_playing_title);
        video_playing_id = video_list.get(rand.nextInt(video_list.size())).getVideoId();
        video_playing_title = videoLibrary.getVideo(video_playing_id).getTitle();
        System.out.println("Playing video: " + video_playing_title);
      }
    } else{
      System.out.println("No videos available");
    }
  }

  public void pauseVideo() {
    if(video_in_play){
      if(!paused){
        System.out.println("Pausing video: " + video_playing_title);
        setPaused(true);
      } else{
        System.out.println("Video already paused: " + video_playing_title);
      }
    } else{
      System.out.println("Cannot pause video: No video is currently playing");
    }
  }

  public void continueVideo() {
    if(video_in_play){
      if(paused){
        System.out.println("Continuing video: " + video_playing_title);
        setPaused(false);
      } else{
        System.out.println("Cannot continue video: Video is not paused");
      }
    } else{
      System.out.println("Cannot continue video: No video is currently playing");
    }
  }

  public void showPlaying() {
    if(video_in_play){
      if(isPaused()){
        System.out.println("Currently playing: " + videoLibrary.getVideo(video_playing_id).getInfo() + " - PAUSED");
      } else{
        System.out.println("Currently playing: " + videoLibrary.getVideo(video_playing_id).getInfo());
      }
    } else{
      System.out.println("No video is currently playing");
    }
  }

  public void createPlaylist(String playlistName) {
    String playlistName_lower = playlistName.toLowerCase();
    if(!playlists_map.containsKey(playlistName_lower)){
      playlistsName_actual.add(playlistName);
      System.out.println("Successfully created new playlist: " + playlistName);
      playlists_map.put(playlistName_lower,new VideoPlaylist());
    } else{
      System.out.println("Cannot create playlist: A playlist with the same name already exists");
    }
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    Video video = videoLibrary.getVideo(videoId);
    if(playlists_map.containsKey(playlistName.toLowerCase())){
      if(video != null) {
        if (!video.isFlagged()) {
          if (!playlists_map.get(playlistName.toLowerCase()).getVideo_list().contains(video)) {
            playlists_map.get(playlistName.toLowerCase()).getVideo_list().add(video);
            System.out.println("Added video to " + playlistName + ": " + videoLibrary.getVideo(videoId).getTitle());
          } else {
            System.out.println("Cannot add video to " + playlistName + ": Video already added");
          }
        } else {
          System.out.println("Cannot add video to " + playlistName + ": Video is currently flagged " + videoLibrary.getVideo(videoId).getFlag_reason());
        }
      } else{
        System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
      }
    } else{
      System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
    }
  }

  public void showAllPlaylists() {
    if(playlistsName_actual.size() != 0){
      System.out.println("Showing all playlists:");
      Collections.sort(playlistsName_actual);
      for(String s : playlistsName_actual){
        System.out.println(s);
      }
    } else{
      System.out.println("No playlists exist yet");
    }
  }

  public void showPlaylist(String playlistName) {
    if(playlists_map.get(playlistName.toLowerCase()) != null){
      System.out.println("Showing playlist: " + playlistName);
      if(playlists_map.get(playlistName.toLowerCase()).getVideo_list().size() != 0){
        for(Video video : playlists_map.get(playlistName.toLowerCase()).getVideo_list()){
          if(video.isFlagged()){
            System.out.println(video.getFull_info());
          } else {
            System.out.println(video.getInfo());
          }
        }
      } else{
        System.out.println("No videos here yet");
      }
    } else{
      System.out.println("Cannot show playlist " + playlistName + ": Playlist does not exist");
    }
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    Video video = videoLibrary.getVideo(videoId);
    if(playlists_map.containsKey(playlistName.toLowerCase())){
      if(videoLibrary.getVideo(videoId) != null){
        if(playlists_map.get(playlistName.toLowerCase()).getVideo_list().contains(video)){
          playlists_map.get(playlistName.toLowerCase()).getVideo_list().remove(video);
          System.out.println("Removed video from " + playlistName + ": " + video.getTitle());
        } else{
          System.out.println("Cannot remove video from " + playlistName + ": Video is not in playlist");
        }
      } else{
        System.out.println("Cannot remove video from " + playlistName + ": Video does not exist");
      }
    } else{
      System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
    }
  }

  public void clearPlaylist(String playlistName) {
    if(playlists_map.containsKey(playlistName.toLowerCase())){
      playlists_map.get(playlistName.toLowerCase()).getVideo_list().clear();
      System.out.println("Successfully removed all videos from " + playlistName);
    } else{
      System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exist");
    }
  }

  public void deletePlaylist(String playlistName) {
    if(playlists_map.containsKey(playlistName.toLowerCase())){
      playlists_map.remove(playlistName.toLowerCase());
      playlistsName_actual.remove(playlistName);
      System.out.println("Deleted playlist: " + playlistName);
    } else{
      System.out.println("Cannot delete playlist "+ playlistName + ": Playlist does not exist");
    }
  }

  public void searchVideos(String searchTerm) {
    int term_count = 1;
    ArrayList<Video> video_list = new ArrayList<>();
    for(Video video : videoLibrary.getVideos()){
      if(!video.isFlagged()) {
        if (video.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
          term_count++;
          video_list.add(video);
        }
      }
    }

    video_list.sort(Comparator.comparing(Video::getTitle));
    if(term_count > 1){
      System.out.println("Here are the results for " + searchTerm + ":");
      for(int i =1; i< term_count; i++){
        System.out.println(i + ") " + video_list.get(i-1).getInfo());
      }
      System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
      System.out.println("If your answer is not a valid number, we will assume it's a no.");
      Scanner in = new Scanner(System.in);
      try {
         int index = in.nextInt();
         playVideo(video_list.get(index-1).getVideoId());
      } catch (Exception ignored){
      }
      in.close();
    } else{
      System.out.println("No search results for " + searchTerm);
    }
  }

  public void searchVideosWithTag(String videoTag) {
    int term_count = 1;
    ArrayList<Video> video_list = new ArrayList<>();
    for(Video video : videoLibrary.getVideos()){
      if(!video.isFlagged()) {
        for (String tag : video.getTags()) {
          if (tag.equalsIgnoreCase(videoTag)) {
            term_count++;
            video_list.add(video);
          }
        }
      }
    }

    video_list.sort(Comparator.comparing(Video::getTitle));
    if(term_count > 1){
      System.out.println("Here are the results for " + videoTag + ":");
      for(int i =1; i< term_count; i++){
        System.out.println(i + ") " + video_list.get(i-1).getInfo());
      }
      System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
      System.out.println("If your answer is not a valid number, we will assume it's a no.");
      Scanner in = new Scanner(System.in);
      try {
        int index = in.nextInt();
        playVideo(video_list.get(index-1).getVideoId());
      } catch (Exception ignored){
      }
      in.close();
    } else{
      System.out.println("No search results for " + videoTag);
    }
  }

  public void flagVideo(String videoId) {
    Video video = videoLibrary.getVideo(videoId);
    if(video != null){
      if(!video.isFlagged()){
        video.setFlagged(true);
        video.setFlag_reason("Not supplied");
        if(video_playing_id != null) {
          if (video_playing_id.equals(videoId)) {
            stopVideo();
          }
        }
        System.out.println("Successfully flagged video: " + video.getFlag_info());
      } else{
        System.out.println("Cannot flag video: Video is already flagged");
      }
    } else{
      System.out.println("Cannot flag video: Video does not exist");
    }
  }

  public void flagVideo(String videoId, String reason) {
    Video video = videoLibrary.getVideo(videoId);
    if(video != null){
      if(!video.isFlagged()){
        video.setFlagged(true);
        video.setFlag_reason(reason);
        if(video_playing_id != null) {
          if (video_playing_id.equals(videoId)) {
            stopVideo();
          }
        }
        System.out.println("Successfully flagged video: " + video.getFlag_info());
      } else{
        System.out.println("Cannot flag video: Video is already flagged");
      }
    } else{
      System.out.println("Cannot flag video: Video does not exist");
    }
  }

  public void allowVideo(String videoId) {
    Video video = videoLibrary.getVideo(videoId);
    if(video != null){
      if(video.isFlagged()){
        video.setFlagged(false);
        video.setFlag_reason(null);
        System.out.println("Successfully removed flag from video: " + video.getTitle());
      } else{
        System.out.println("Cannot remove flag from video: Video is not flagged");
      }
    } else{
      System.out.println("Cannot remove flag from video: Video does not exist");
    }
  }
}