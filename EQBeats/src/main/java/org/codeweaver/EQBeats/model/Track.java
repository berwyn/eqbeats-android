package org.codeweaver.eqbeats.model;

/**
 * Created by Berwyn Codeweaver on 23/06/13.
 */
public class Track extends Model {

	private String	title;
	private User	artist;
	private Media	download;
	private Media	stream;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getArtist() {
		return artist;
	}

	public void setArtist(User artist) {
		this.artist = artist;
	}

	public Media getDownload() {
		return download;
	}

	public void setDownload(Media download) {
		this.download = download;
	}

	public Media getStream() {
		return stream;
	}

	public void setStream(Media stream) {
		this.stream = stream;
	}

}
