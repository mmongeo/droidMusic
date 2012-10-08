package ac.cr.ecci.ucr.droidmusic.service;

import java.util.ArrayList;
import java.util.List;

import ac.cr.ecci.ucr.droidmusic.bo.Song;
import ac.cr.ecci.ucr.droidmusic.ui.DroidMusicService;

public class SimpleDroidMusicService extends DroidMusicService {
	int max = 30; //numero maximo de canciones por consulta
	int current = 0;
	
	private void simulateBlock() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException ignore) {
		}
	}
	
	public void setCurrent(int current){
		this.current = current;		
	}
	
	public boolean isFinished(){ //si ya termino de encontrar canciones
		return current  > max - 1;		
	}
	
	private List<Song> createSongs(int numberOfSongs){ //crea las canciones
		List<Song> newSongs = new ArrayList<Song>();
		for(int i = 0; i < numberOfSongs ; ++i){
			newSongs.add( new Song("Cancion " + (current +  i),"Mario",null));
		}	
		current+=numberOfSongs;
		return newSongs;
	}
	
	public SimpleDroidMusicService(){
				
	}
	@Override
	public List<Song> getSongs(int numberOfSongs) { //le devuelve el numero requerido de canciones
		simulateBlock();
		List<Song> songs = new ArrayList<Song>();
		if(numberOfSongs != 0){
			songs = createSongs(numberOfSongs);
		}
		else{
			songs = null;			
		}
		return songs;
	}

}
