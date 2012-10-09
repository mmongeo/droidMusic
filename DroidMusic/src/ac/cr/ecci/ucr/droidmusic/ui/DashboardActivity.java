package ac.cr.ecci.ucr.droidmusic.ui;

import ac.cr.ecci.droidmusic.actionbar.ActionBarActivity;
import ac.cr.ecci.ucr.droidmusic.R;
import ac.cr.ecci.ucr.droidmusic.R.layout;
import ac.cr.ecci.ucr.droidmusic.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class DashboardActivity extends ActionBarActivity {

	OnClickListener listener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		Button buttonAux = (Button) findViewById(R.id.boton_buscar);
		buttonAux.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						ListaCancionesActivity.class));

			}
		});
		buttonAux = (Button) findViewById(R.id.boton_generos);
		buttonAux.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Button button = (Button) v;
				startActivity(new Intent(getApplicationContext(),
						GenerosActivity.class));
			}
		});
		buttonAux = (Button) findViewById(R.id.boton_listas);
		buttonAux.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Button button = (Button) v;
				startActivity(new Intent(getApplicationContext(),
						ListasActivity.class));

			}
		});
		buttonAux = (Button) findViewById(R.id.boton_favoritos);
		buttonAux.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Button button = (Button) v;
				startActivity(new Intent(getApplicationContext(),
						GenerosActivity.class));

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_dashboard, menu);
		return true;
	}
}
