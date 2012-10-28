package ac.cr.ecci.ucr.droidmusic.service;



public class DroidMusicServiceFactory {
	private static DroidMusicService service = null;
		
	public static DroidMusicService getInstance(){
		if(service == null)	{
			service = new ItunesMusicService();			
		}
		return service;
	}
	private DroidMusicServiceFactory(){
				
	}
}
