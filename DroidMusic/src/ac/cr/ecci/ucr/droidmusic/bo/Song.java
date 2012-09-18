package ac.cr.ecci.ucr.droidmusic.bo;

public class Song {
	String songName; 
	String artist;
	Object thumbnail;
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public Object getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(Object thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public Song(String songName, String artist, Object thumbnail) {
		this.songName = songName;
		this.artist = artist;
		this.thumbnail = thumbnail;
	} 
	
	
	
	
}
