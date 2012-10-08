package ac.cr.ecci.ucr.droidmusic.service;

import ac.cr.ecci.ucr.droidmusic.ui.DroidMusicService;


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
