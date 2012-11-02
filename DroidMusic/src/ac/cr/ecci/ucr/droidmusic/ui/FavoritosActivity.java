package ac.cr.ecci.ucr.droidmusic.ui;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ac.cr.ecci.ucr.droidmusic.R;
import ac.cr.ecci.ucr.droidmusic.actionbar.ActionBarActivity;
import ac.cr.ecci.ucr.droidmusic.bo.MusicAdapter;
import ac.cr.ecci.ucr.droidmusic.bo.RetainedInstances;
import ac.cr.ecci.ucr.droidmusic.bo.Song;
import ac.cr.ecci.ucr.droidmusic.bo.TaskSaveFavorite;
import ac.cr.ecci.ucr.droidmusic.data.DBHelper;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.j256.ormlite.dao.Dao;

public class FavoritosActivity extends ActionBarActivity {
	ListView mListCanciones;
	TaskAllSongs mTask = null;
	RetainedInstances mInstance;
	MusicAdapter mAdapter = null;
	AlertDialog dialog;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favoritos);

		if (VERSION.SDK_INT > 10) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		List<Song> canciones = new ArrayList<Song>();

		RetainedInstances aux;
		Object obj = getLastNonConfigurationInstance();
		if (obj != null && obj instanceof RetainedInstances) {
			aux = (RetainedInstances) obj;
			if (aux.getTask() != null && aux.getTask() instanceof TaskAllSongs) {
				mTask = (TaskAllSongs) aux.getTask();
				mTask.attach(this);
			}
			if (aux.getAdapter() != null) {
				mAdapter = aux.getAdapter();
			}
			if (aux.getDialog() != null) {
				dialog = aux.getDialog();
			}
		}

		mListCanciones = (ListView) findViewById(R.id.layout_listaFavoritos);
		View footer = ((LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.item_boton_mas, null, false);
		Button btn = (Button) footer.findViewById(R.id.boton_mas);
		btn.setEnabled(false);
		btn.setText("No hay mas favoritos");
		mListCanciones.addFooterView(footer);
		if (mAdapter == null) {
			mAdapter = new MusicAdapter(getApplicationContext(), canciones,
					true, this);
		}
		mListCanciones.setAdapter(mAdapter);
		if (obj == null) { // si es la primera vez
			TaskAllSongs task = new TaskAllSongs(this, getApplicationContext(),
					mAdapter);
			task.execute();
		}
		if (dialog != null) { // para mostrarlos hasta despues de actualizar la
								// lista
			dialog.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_favoritos, menu);

		return true;
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

	private static class TaskAllSongs extends AsyncTask<Void, Void, List<Song>> {
		Context context;
		WeakReference<Activity> ctx;
		MusicAdapter adapter;

		public TaskAllSongs(Activity activity, Context context,
				MusicAdapter adapter) {
			super();
			this.context = context;
			attach(activity);
			this.adapter = adapter;
		}

		@Override
		protected List<Song> doInBackground(Void... params) {
			DBHelper helper = DBHelper.getHelper(context);
			Dao<Song, Integer> dao;
			List<Song> canciones = new ArrayList<Song>();
			try {
				dao = helper.getDao();
				canciones = dao.queryForAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return canciones;

		}

		@Override
		protected void onPostExecute(List<Song> result) {
			this.adapter.addList(result);

			super.onPostExecute(result);
		}

		public void attach(Activity activity) {
			this.ctx = new WeakReference(activity);
		}

		public void deattach() {
			this.ctx = null;
		}
	}

	public Object onRetainNonConfigurationInstance() {
		mInstance = new RetainedInstances();
		mInstance.setEstadoFooter(RetainedInstances.FOOTERNOHAY);
		if (mAdapter != null) {
			mInstance.setAdapter(mAdapter);
		}
		if (dialog != null) {
			dialog.dismiss();
			mInstance.setDialog(dialog);
			dialog = null;
		}
		if (mTask != null) {
			mInstance.setTask(mTask);
			mTask.deattach();
			return mInstance;
		}
		return mInstance;
	}

	public void createConfirmDialog(final Song song,
			final ImageButton buttonFavorites) { // mensaje de confirmacion para
													// borrar una cancion de
													// favoritos
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final Context context = getApplicationContext();
		// Add the buttons
		builder.setMessage(R.string.texto_borrar_favorito);
		builder.setTitle(R.string.confirmacion);
		builder.setPositiveButton(R.string.confirmar_borrar,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						TaskSaveFavorite taskFavorite = new TaskSaveFavorite(
								mAdapter);
						taskFavorite.setButtonPressed(buttonFavorites);
						taskFavorite.setContext(context);
						taskFavorite.execute(song);
					}
				});
		builder.setNegativeButton(R.string.cancelar,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// User cancelled the dialog
					}
				});
		// Create the AlertDialog
		dialog = builder.create();
		dialog.show();
	}

}
