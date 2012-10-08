package ac.cr.ecci.ucr.droidmusic.ui;

import ac.cr.ecci.droidmusic.actionbar.ActionBarActivity;
import ac.cr.ecci.ucr.droidmusic.R;
import ac.cr.ecci.ucr.droidmusic.R.layout;
import ac.cr.ecci.ucr.droidmusic.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DashboardActivity extends ActionBarActivity {

	Button mButtonBuscar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		mButtonBuscar = (Button) findViewById(R.id.boton_buscar);
		mButtonBuscar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity( new Intent(getApplicationContext(), ListaCancionesActivity.class));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_dashboard, menu);
		return true;
	}
}
