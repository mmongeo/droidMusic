package ac.cr.ecci.ucr.droidmusic.ui;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import ac.cr.ecci.droidmusic.actionbar.ActionBarActivity;
import ac.cr.ecci.ucr.droidmusic.R;
import ac.cr.ecci.ucr.droidmusic.bo.Song;

import ac.cr.ecci.ucr.droidmusic.service.DroidMusicServiceFactory;
import ac.cr.ecci.ucr.droidmusic.service.SimpleDroidMusicService;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ListaCancionesActivity extends ActionBarActivity {

	static ListView mListCanciones;
	ImageButton mButtonBuscar;
	static Button mButtonVerMas;
	View mFooterView;
	static int mEstadoFooter;
	TaskSongs mTask;
	RetainedInstances mInstance;

	public static final int PROGRESS_DIALOG = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		List<Song> canciones = new ArrayList<Song>();
		RetainedInstances aux = new RetainedInstances();
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

		setContentView(R.layout.activity_lista_canciones);
		mButtonBuscar = (ImageButton) findViewById(R.id.boton_buscar);
		mListCanciones = (ListView) findViewById(R.id.layout_ListaCanciones);
		EditText textoBuscar = (EditText) findViewById(R.id.texto_busqueda);
		textoBuscar.setOnEditorActionListener(new OnEditorActionListener() {
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

		mFooterView = ((LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.item_boton_mas, null, false);
		mListCanciones.addFooterView(mFooterView);
		mButtonVerMas = (Button) findViewById(R.id.boton_mas);
		mFooterView.setVisibility(View.INVISIBLE);
		
		if(aux!= null){
			mEstadoFooter = aux.getEstadoFooter();			//si se crea solo con el controstructor por omision no le devuelve cero que es que esta invisible
		}
		
		mListCanciones.setAdapter(new MusicAdapter(this, canciones));
		mButtonBuscar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pressSearchButton();
			}
		});
		mButtonVerMas.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mTask = new TaskSongs(ListaCancionesActivity.this);
				mTask.setFirst(false);
				mTask.execute();
			}
		});
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
		if (((EditText) findViewById(R.id.texto_busqueda)).getText().toString()
				.contentEquals("")) { // si no tiene nada no devuelve resultados
			mTask.setEmpty(true);
			mEstadoFooter = RetainedInstances.FOOTERINVIS; 
		} else {
			mTask.setEmpty(false);
		}
		mTask.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_lista_canciones, menu);
		return true;
	}

	private static class MusicAdapter extends BaseAdapter {
		List<Song> songs;
		LayoutInflater inflater;

		public MusicAdapter(Context context, List<Song> songs) {
			this.songs = songs;
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (this.songs != null && !this.songs.isEmpty()) {
				notifyDataSetChanged();
				
			}
		}

		public void addList(List<Song> songs) {
			this.songs.addAll(songs);
			notifyDataSetChanged();
		}

		public List<Song> getSongs() {
			return songs;
		}

		public void clear() {
			this.songs.clear();
			notifyDataSetChanged();
		}

		public int getCount() {
			return songs.size();
		}

		public Object getItem(int position) {
			return songs.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			Song song = (Song) getItem(position);

			SongViewHolder holder = null;
			if (convertView == null) {

				convertView = inflater.inflate(R.layout.item_cancion, parent,
						false);
				holder = new SongViewHolder();

				convertView.setTag(holder);

				holder.artist = (TextView) convertView
						.findViewById(R.id.item_Artista);
				holder.title = (TextView) convertView
						.findViewById(R.id.item_Cancion);

			} else {
				holder = (SongViewHolder) convertView.getTag();
			}

			holder.artist.setText(song.getArtist());
			holder.title.setText(song.getSongName());

			return convertView;
		}
	}

	private static class RetainedInstances {
		private TaskSongs task;
		private List<Song> songs;
		private int estadoFooter =0;
		public static final int FOOTERINVIS = 0;
		public static final int FOOTERVIS = 1;
		public static final int FOOTERNOHAY = 2;
		public static final int FOOTERVERMAS = 3;
		
		public RetainedInstances() {

		}

		public TaskSongs getTask() {
			return task;
		}

		public void setTask(TaskSongs task) {
			this.task = task;
		}

		public List<Song> getSongs() {
			return songs;
		}

		public void setSongs(List<Song> songs) {
			this.songs = songs;
		}

		public int getEstadoFooter() {
			return estadoFooter;
		}

		public void setEstadoFooter(int estadoFooter) {
			this.estadoFooter = estadoFooter;
		}
		
		

	}

	private static class SongViewHolder {
		public TextView title;
		public TextView artist;
		public ImageView photoThumbnail;
	}

	private static class TaskSongs extends AsyncTask<Void, Void, List<Song>> {
		boolean empty = false; // para que no devuelva ningun resultado
		boolean first = true; // para que haga la primer busqueda
		ProgressDialog progress;
		boolean finish = false;
		WeakReference ctx;

		public TaskSongs(Activity activity) {
			super();
			attach(activity);
		}

		public void setEmpty(boolean empty) {
			this.empty = empty;
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

			if (empty == false) {
				SimpleDroidMusicService service = (SimpleDroidMusicService) DroidMusicServiceFactory
						.getInstance();
				if (first) {
					service.setCurrent(0);
				}
				try {
					songs = service.getSongs(10);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finish = service.isFinished();
			} else {
				finish = true;
			}
			return songs;
		}
		
		private void revisarFooter(int modo){
			switch (modo) {
			case RetainedInstances.FOOTERINVIS:
				
				break;
			case RetainedInstances.FOOTERVIS:
				break;
			case RetainedInstances.FOOTERVERMAS:
				break;
			case RetainedInstances.FOOTERNOHAY:
				break;
				
			}
			
		}

		@Override
		protected void onPostExecute(List<Song> result) {
			if (ctx != null && ctx.get() != null) {
				MusicAdapter adapter = (MusicAdapter) ((HeaderViewListAdapter) mListCanciones
						.getAdapter()).getWrappedAdapter();
				adapter.addList(result);
				Activity activity = (Activity) ctx.get();
				activity.dismissDialog(PROGRESS_DIALOG);
				if (finish) {
					mButtonVerMas.setEnabled(false);
					mButtonVerMas.setText("No hay mas resultados");
					mEstadoFooter = RetainedInstances.FOOTERNOHAY;
					
				} else {
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
