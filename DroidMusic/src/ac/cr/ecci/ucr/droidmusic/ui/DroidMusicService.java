package ac.cr.ecci.ucr.droidmusic.ui;

import java.util.List;

import ac.cr.ecci.ucr.droidmusic.bo.Song;

public abstract class DroidMusicService {
	public abstract List<Song> getSongs(int quantity);
}
