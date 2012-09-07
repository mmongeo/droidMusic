package ac.cr.ecci.ucr.droidmusic.ui;

import ac.cr.ecci.ucr.droidmusic.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

public class SplashActivity extends Activity {

	Handler mHandler = new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_splash, menu);
        return true;
    }

	@Override
	protected void onResume() {
		super.onResume();
		mHandler.postDelayed(new Runnable() {

			public void run() {
				startActivity( new Intent(getApplicationContext(), ListaCancionesActivity.class));
				finish();
			}
		}, 2000);
		
	}
    
    
}
