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

	abstract class Media {
		private String	art;
		private String	opus;
		private String	vorbis;
		private String	aac;
		private String	mp3;
		private String	original;

		public String getArt() {
			return art;
		}

		public void setArt(String art) {
			this.art = art;
		}

		public String getOpus() {
			return opus;
		}

		public void setOpus(String opus) {
			this.opus = opus;
		}

		public String getVorbis() {
			return vorbis;
		}

		public void setVorbis(String vorbis) {
			this.vorbis = vorbis;
		}

		public String getAac() {
			return aac;
		}

		public void setAac(String aac) {
			this.aac = aac;
		}

		public String getMp3() {
			return mp3;
		}

		public void setMp3(String mp3) {
			this.mp3 = mp3;
		}

		public String getOriginal() {
			return original;
		}

		public void setOriginal(String original) {
			this.original = original;
		}
	}
}
