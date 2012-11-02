package ac.cr.ecci.ucr.droidmusic.bo;

public class ItunesServiceConectionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 999;

	@Override
	public String getMessage() {
		return "No se ha podido establecer una conexion con el servidor de Itunes \n" +
				"revise que haya conexion a Internet o intentelo mas tarde.";
	}
	
	public ItunesServiceConectionException() {
		// TODO Auto-generated constructor stub
	}
	
}
