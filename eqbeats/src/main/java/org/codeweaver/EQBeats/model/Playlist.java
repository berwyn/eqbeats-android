package org.codeweaver.eqbeats.model;

/**
 * Created by Berwyn Codeweaver on 23/06/13.
 */
public class Playlist extends Model {

    private String name;
    private User author;
    private Track[] tracks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Track[] getTracks() {
        return tracks;
    }

    public void setTracks(Track[] tracks) {
        this.tracks = tracks;
    }
}
