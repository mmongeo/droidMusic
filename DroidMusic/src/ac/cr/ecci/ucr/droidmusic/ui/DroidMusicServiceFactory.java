package ac.cr.ecci.ucr.droidmusic.ui;

import ac.cr.ecci.ucr.droidmusic.service.DroidMusicService;
import ac.cr.ecci.ucr.droidmusic.service.SimpleDroidMusicService;

public class DroidMusicServiceFactory {
	private static DroidMusicService service = null;
		
	public static DroidMusicService getInstance(){
		if(service == null)	{
			service = new SimpleDroidMusicService();			
		}
		return service;
	}
	private DroidMusicServiceFactory(){
				
	}
}
