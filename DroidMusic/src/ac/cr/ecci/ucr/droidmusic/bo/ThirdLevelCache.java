package ac.cr.ecci.ucr.droidmusic.bo;

import java.io.File;
import android.content.Context;
import android.util.Log;
 /*
  * Este codigo no es hecho por mi, lo tome de :
  *  https://github.com/thest1/LazyList/blob/master/src/com/fedorvlasov/lazylist/FileCache.java
  *  Es una clase simple que tiene el directorio y se encarga de traer y guardar imagenes en cache
  * */
public class ThirdLevelCache {
    
    private File cacheDir;
    
    public ThirdLevelCache(Context context){
        //Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"artwork");
        else
            cacheDir=context.getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();
    }
    
    public File getFile(String url){
    	Log.d("cache","salvo" + url + "estoy" + cacheDir.getPath());
        //I identify images by hashcode. Not a perfect solution, good for the demo.
        String filename = url;
        //Another possible solution (thanks to grantland)
        //String filename = URLEncoder.encode(url);
        File f = new File(cacheDir, filename);
        return f;
        
    }
    
    public void clear(){
        File[] files=cacheDir.listFiles();
        if(files==null)
            return;
        for(File f:files)
            f.delete();
    }
    

}