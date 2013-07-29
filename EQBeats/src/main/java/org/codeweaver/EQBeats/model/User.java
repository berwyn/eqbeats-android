package org.codeweaver.eqbeats.model;

/**
 * Created by Berwyn Codeweaver on 23/06/13.
 */
public class User extends Model {

    private String     name;
    private String     avatar;
    private Track[]    tracks;
    private long[]     trackIds;
    private Playlist[] playlists;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Track[] getTracks() {
        return tracks;
    }

    public void setTracks(Track[] tracks) {
        this.tracks = tracks;
    }

    public long[] getTrackIds() {
        return trackIds;
    }

    public void setTrackIds(long[] trackIds) {
        this.trackIds = trackIds;
    }

    public Playlist[] getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Playlist[] playlists) {
        this.playlists = playlists;
    }

}
