package ac.cr.ecci.ucr.droidmusic.ui;

import java.util.List;

import org.w3c.dom.Text;

import ac.cr.ecci.ucr.droidmusic.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends Activity {

	Handler mHandler = new Handler();
	Animation mAnimation;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    	TextView titulo = (TextView) findViewById(R.id.texto_centro);
        TextView textoExplicativo = (TextView) findViewById(R.id.texto_espere);
    	mAnimation = AnimationUtils.loadAnimation(this,R.anim.movetocenter);
        textoExplicativo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein));
    	titulo.startAnimation(mAnimation);
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
				startActivity( new Intent(getApplicationContext(), DashboardActivity.class));
				finish();
			}
		}, 3000);
		
	}
    
    
}
