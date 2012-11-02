package ac.cr.ecci.ucr.droidmusic.bo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.client.ClientProtocolException;

import ac.cr.ecci.ucr.droidmusic.service.HTTPClientFactory;
import ac.cr.ecci.ucr.droidmusic.service.HTTPClientFactory.ClientType;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class TaskArtwork extends AsyncTask<String, Void, Bitmap> {
	ThirdLevelCache cache;
	ImageView imagen;

	TaskArtwork(ThirdLevelCache cache, ImageView imagen) {
		this.cache = cache;
		this.imagen = imagen;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		Log.d("busco","" + params[0]);
		int pos = params[0].lastIndexOf("/");
		String nombre = params[0].substring(pos + 1);
		File cached = cache.getFile(nombre);
		Bitmap bitmap = null;
		boolean errorGrave = false;
		boolean errorConexion = false;
		
		if (!cached.exists()) {
	
			OutputStream fOut = null;
			InputStream in = null;
			try {
				in = HTTPClientFactory.getInputStream(params[0], ClientType.HTTP_URL_CONNECTION);
			} catch (ClientProtocolException e1) {
				errorConexion = true;
			} catch (IOException e1) {
				errorConexion = true;
			}
			
			if(!errorConexion){ //si no hay error de conexion...
				try {
					fOut = new FileOutputStream(cached);
				} catch (FileNotFoundException e) {
					Log.d("CacheDM","NO existe el archivo... creandolo.");
				} catch (IOException e) {
					errorGrave = true;
				}
				finally{
					if(!errorGrave){
						bitmap = BitmapFactory.decodeStream(in);
						
						bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fOut);
		
						try {
							fOut.flush();
							fOut.close();
						} catch (IOException e) {		}
					}
					
				}
			}
		}
		
		
		if (bitmap == null && !errorGrave) { // si lo tiene en cache
			bitmap = BitmapFactory.decodeFile(cached.getPath());
		}
		else{
			return null;			
		}

		return bitmap;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		if(result != null){
		imagen.setImageBitmap(result);
		}
		super.onPostExecute(result);
	}
	
	
	

}
