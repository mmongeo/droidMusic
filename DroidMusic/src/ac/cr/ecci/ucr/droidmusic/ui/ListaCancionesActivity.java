package ac.cr.ecci.ucr.droidmusic.ui;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ac.cr.ecci.ucr.droidmusic.R;
import ac.cr.ecci.ucr.droidmusic.actionbar.ActionBarActivity;
import ac.cr.ecci.ucr.droidmusic.bo.ItunesServiceConectionException;
import ac.cr.ecci.ucr.droidmusic.bo.MusicAdapter;
import ac.cr.ecci.ucr.droidmusic.bo.RetainedInstances;
import ac.cr.ecci.ucr.droidmusic.bo.Song;
import ac.cr.ecci.ucr.droidmusic.data.DBHelper;
import ac.cr.ecci.ucr.droidmusic.service.DroidMusicServiceFactory;
import ac.cr.ecci.ucr.droidmusic.service.ItunesMusicService;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

import com.j256.ormlite.dao.Dao;

public class ListaCancionesActivity extends ActionBarActivity {

	static ListView mListCanciones;
	ImageButton mButtonBuscar;
	static Button mButtonVerMas;
	View mFooterView;
	static int mEstadoFooter;
	TaskSongs mTask;
	RetainedInstances mInstance;
	EditText mTextoBuscar;

	public static final int PROGRESS_DIALOG = 1;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_canciones);

		List<Song> canciones = new ArrayList<Song>();
		RetainedInstances aux = new RetainedInstances();

		if (VERSION.SDK_INT > 10) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		Object obj = getLastNonConfigurationInstance();
		if (obj != null && obj instanceof RetainedInstances) {
			aux = (RetainedInstances) obj;
			if (aux.getTask() != null && aux.getTask() instanceof TaskSongs) {
				mTask = (TaskSongs) aux.getTask();
				mTask.attach(this);
			}
			if (aux.getSongs() != null && aux.getTask() != null) {
				canciones = aux.getSongs();
			}
		}
		mButtonBuscar = (ImageButton) findViewById(R.id.boton_buscar);
		mListCanciones = (ListView) findViewById(R.id.layout_ListaCanciones);
		mTextoBuscar = (EditText) findViewById(R.id.texto_busqueda);
		mTextoBuscar.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (event != null
						&& (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					pressSearchButton();
				}
				return false;
			}
		});

		if (aux != null) {
			mEstadoFooter = aux.getEstadoFooter(); // si se crea solo con el
													// controstructor por
													// omision no le devuelve
													// cero que es que esta
													// invisible
		}
		mFooterView = ((LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.item_boton_mas, null, false);
		mListCanciones.addFooterView(mFooterView);
		mListCanciones.setAdapter(new MusicAdapter(this, canciones, false));
		mButtonBuscar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pressSearchButton();
			}
		});
		mButtonVerMas = (Button) findViewById(R.id.boton_mas);
		mButtonVerMas.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mTask = new TaskSongs(ListaCancionesActivity.this);
				mTask.setFirst(false);
				mTask.execute();
			}
		});

		if (aux != null) { // para ver si estaba o no el footer luego del cambio
							// de orientacion
			RetainedInstances.revisarFooter(aux.getEstadoFooter(), mFooterView,
					mButtonVerMas);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case PROGRESS_DIALOG:
			ProgressDialog pd = new ProgressDialog(this);
			pd.setTitle("Trabajando");
			pd.setMessage("Por favor espere...");
			return pd;

		default:
			break;
		}
		return super.onCreateDialog(id);
	}

	public Object onRetainNonConfigurationInstance() {
		mInstance = new RetainedInstances();
		MusicAdapter adapter = (MusicAdapter) ((HeaderViewListAdapter) mListCanciones
				.getAdapter()).getWrappedAdapter();
		boolean hayCanciones = false;
		mInstance.setEstadoFooter(mEstadoFooter);
		if (adapter.getSongs() != null && !adapter.getSongs().isEmpty()) {
			mInstance.setSongs(adapter.getSongs());
			hayCanciones = true;
		}

		if (mTask != null) {
			mInstance.setTask(mTask);
			mTask.deattach();
			return mInstance;
		}
		if (hayCanciones) {
			return mInstance;
		}

		return super.onRetainNonConfigurationInstance();
	}

	private void pressSearchButton() {
		mFooterView.setVisibility(View.VISIBLE);
		mEstadoFooter = RetainedInstances.FOOTERVIS;
		MusicAdapter adapter = (MusicAdapter) ((HeaderViewListAdapter) mListCanciones
				.getAdapter()).getWrappedAdapter();
		adapter.clear();

		mTask = new TaskSongs(this);
		mTask.setFirst(true); // es el primer pedido de busqueda
		if (!(mTextoBuscar.getText().toString().contentEquals(""))) {
			// si no tiene nada no se llama al servico
			Log.d("presssearch", "entra " + mTextoBuscar.getText().toString());
			mTask.setSearchCriteria(mTextoBuscar.getText().toString());
		}
		mTask.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_lista_canciones, menu);
		return true;
	}

	private static class TaskSongs extends AsyncTask<Void, String, List<Song>> {
		String searchCriteria = ""; // para que no devuelva ningun resultado
		boolean first = true; // para que haga la primer busqueda
		ProgressDialog progress;
		boolean finish = false;
		WeakReference ctx;

		public TaskSongs(Activity activity) {
			super();
			attach(activity);
		}

		public void setSearchCriteria(String searchCriteria) {
			this.searchCriteria = searchCriteria;
		}

		public void setFirst(boolean first) {
			this.first = first;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Activity activity = (Activity) ctx.get();
			if (activity != null && !activity.isFinishing()) {
				// progress = ProgressDialog.show(activity, "Espere",
				// "Buscando canciones...");
				activity.showDialog(PROGRESS_DIALOG);
			}
		}

		protected List<Song> doInBackground(Void... params) {
			List<Song> songs = new ArrayList<Song>();
			ItunesMusicService service = (ItunesMusicService) DroidMusicServiceFactory
					.getInstance();
			boolean error = false;
			if (first) {
				try {
					if (!service.newSearch(searchCriteria)) {
						error = true;
					}
				} catch (ItunesServiceConectionException e) {
					this.publishProgress(e.getMessage());		
					error = true;
				}
			}

			if (!error) {
				try {
					songs = service.getSongs(10);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return songs;
		}

		
		
		@Override
		protected void onProgressUpdate(String... values) {
			(Toast.makeText( ((Activity) ctx.get()).getApplicationContext() ,values[0] , Toast.LENGTH_SHORT)).show();
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(List<Song> result) {
			if (ctx != null && ctx.get() != null) {
				if (result.isEmpty()) {
					mButtonVerMas.setEnabled(false);
					mButtonVerMas.setText("No hay mas resultados");
					mEstadoFooter = RetainedInstances.FOOTERNOHAY;

				} else {
					MusicAdapter adapter = (MusicAdapter) ((HeaderViewListAdapter) mListCanciones
							.getAdapter()).getWrappedAdapter();
					adapter.addList(result);
					mButtonVerMas.setEnabled(true);
					mButtonVerMas.setText("Ver mas resultados"); // podrmButtonVerMasia
																	// ser mas
																	// especifico
																	// para el
																	// caso que
																	// no hay
																	// reusltados
					mEstadoFooter = RetainedInstances.FOOTERVERMAS;
				}
				Activity activity = (Activity) ctx.get();
				activity.dismissDialog(PROGRESS_DIALOG);
				
				mButtonVerMas.setVisibility(View.VISIBLE);
			}
		}

		public void attach(Activity activity) {
			this.ctx = new WeakReference(activity);
		}

		public void deattach() {
			this.ctx = null;
		}

	};

}
