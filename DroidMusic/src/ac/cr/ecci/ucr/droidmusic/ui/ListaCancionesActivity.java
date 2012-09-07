package ac.cr.ecci.ucr.droidmusic.ui;

import ac.cr.ecci.ucr.droidmusic.R;
import ac.cr.ecci.ucr.droidmusic.R.layout;
import ac.cr.ecci.ucr.droidmusic.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ListaCancionesActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_canciones);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_lista_canciones, menu);
        return true;
    }
}
