package ac.cr.ecci.ucr.droidmusic.ui;

import java.util.ArrayList;
import java.util.List;



import ac.cr.ecci.ucr.droidmusic.R;
import ac.cr.ecci.ucr.droidmusic.bo.Song;

import ac.cr.ecci.ucr.droidmusic.service.SimpleDroidMusicService;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ListaCancionesActivity extends Activity {

	ListView mListCanciones;
	ImageButton mButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_canciones);
        mButton = (ImageButton) findViewById(R.id.boton_buscar);
        mListCanciones = (ListView) findViewById(R.id.layout_ListaCanciones);
        mListCanciones.setAdapter(new MusicAdapter(this,new ArrayList<Song>()));
        
        mButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TaskSongs task = new TaskSongs();
				if(((EditText)findViewById(R.id.texto_busqueda)).getText().toString().contentEquals("")){
					task.setEmpty(true);
				}
				else{
					task.setEmpty(false);
				}
				task.execute();
			}
		});         
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
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public void addList(List<Song> songs) {
			this.songs.addAll(songs);
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

				convertView = inflater.inflate(R.layout.item_cancion,parent, false);
				holder = new SongViewHolder();

				convertView.setTag(holder);

				holder.artist = (TextView) convertView.findViewById(R.id.item_Artista);
				holder.title = (TextView) convertView.findViewById(R.id.item_Cancion);

			} else {
				holder = (SongViewHolder) convertView.getTag();
			}

			holder.artist.setText(song.getArtist());
			holder.title.setText(song.getSongName());

			return convertView;
		}
	}

	private static class SongViewHolder {
		public TextView title;
		public TextView artist;
		public ImageView photoThumbnail;
	}
	
	 class TaskSongs extends AsyncTask<Void, Void, List<Song>> {
		boolean empty = false;
		ProgressDialog progress;
	
		public boolean isEmpty() {
			return empty;
		}

		public void setEmpty(boolean empty) {
			this.empty = empty;
		}
		
		

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progress = ProgressDialog.show(ListaCancionesActivity.this, "Espere", "Buscando canciones...");
		}

		@Override
		protected List<Song> doInBackground(Void... params) {
			List<Song> songs = new ArrayList<Song>();
			if(empty == false){
				SimpleDroidMusicService service = (SimpleDroidMusicService) DroidMusicServiceFactory.getInstance();
				
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
		protected void onPostExecute(List<Song> result) {
			
			MusicAdapter adapter = (MusicAdapter) mListCanciones.getAdapter();
			adapter.addList(result);
			progress.dismiss();
		}
	};
    
}
