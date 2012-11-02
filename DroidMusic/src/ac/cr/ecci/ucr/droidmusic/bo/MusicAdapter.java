package ac.cr.ecci.ucr.droidmusic.bo;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;

import ac.cr.ecci.ucr.droidmusic.R;
import ac.cr.ecci.ucr.droidmusic.actionbar.ActionBarActivity;
import ac.cr.ecci.ucr.droidmusic.data.DBHelper;
import ac.cr.ecci.ucr.droidmusic.ui.FavoritosActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MusicAdapter extends BaseAdapter {
	List<Song> songs;
	LayoutInflater inflater;
	Context context;
	boolean eliminar;
	MusicAdapter self = null;
	Activity activity = null;
	Dao<Song, Integer> dao;
	ThirdLevelCache cache;

	public MusicAdapter(Context context, List<Song> pSongs, boolean eliminar) {
		inicializeCommon(context, pSongs, eliminar);
		DBHelper helper = DBHelper.getHelper(context);
		try {
			dao = helper.getDao();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public MusicAdapter(Context context, List<Song> pSongs, boolean eliminar,
			Activity activity) {
		inicializeCommon(context, pSongs, eliminar);
		this.activity = activity;
	}

	private void inicializeCommon(Context context, List<Song> pSongs,
			boolean eliminar) {
		context = context;
		this.eliminar = eliminar;
		this.songs = pSongs;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (this.songs != null && !this.songs.isEmpty()) {
			notifyDataSetChanged();
		}
		self = this;
		this.cache = new ThirdLevelCache(context);
	}

	public void addList(List<Song> songs) {
		this.songs.addAll(songs);
		notifyDataSetChanged();
	}

	public void removeSong(int pos) {
		Log.d("lo borra", "" + pos);
		songs.remove(pos);
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
		final Song song = (Song) getItem(position);

		SongViewHolder holder = null;
		if (convertView == null) {

			convertView = inflater
					.inflate(R.layout.item_cancion, parent, false);
			holder = new SongViewHolder();

			convertView.setTag(holder);

			holder.artist = (TextView) convertView
					.findViewById(R.id.item_Artista);
			holder.title = (TextView) convertView
					.findViewById(R.id.item_Cancion);
			if (activity != null) {
				((ImageButton) convertView.findViewById(R.id.item_estrella))
						.setImageResource(R.drawable.star_on);
			}

		} else {
			holder = (SongViewHolder) convertView.getTag();
		}
		final ImageButton buttonFavorites = (ImageButton) convertView
				.findViewById(R.id.item_estrella);
		buttonFavorites.setTag(R.id.id_tag_boton, position);
		ImageView vista = (ImageView) convertView
				.findViewById(R.id.item_audifono);
		TaskArtwork taskArt = new TaskArtwork(cache, vista);
		taskArt.execute(song.getArtworkUrl30());

		if (activity == null) { // no esta en favorites
			TaskFavorites checkFav = new TaskFavorites(dao, buttonFavorites);
			checkFav.execute(song.getTrackId());
			buttonFavorites.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					TaskSaveFavorite taskFavorite;
					if (eliminar) { // si quiere que elimine del listview
						taskFavorite = new TaskSaveFavorite(self);
					} else {
						taskFavorite = new TaskSaveFavorite();
					}
					taskFavorite.setButtonPressed(buttonFavorites);
					taskFavorite.setContext(context);
					taskFavorite.execute(song);
				}
			});

		} else {
			buttonFavorites.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					((FavoritosActivity) activity).createConfirmDialog(song,
							buttonFavorites);
				}
			});
		}

		holder.artist.setText(song.getArtistName());
		holder.title.setText(song.getTrackName());

		return convertView;
	}

	private static class SongViewHolder {
		public TextView title;
		public TextView artist;
		public ImageView photoThumbnail;
	}

	private static class TaskFavorites extends
			AsyncTask<Integer, Void, Boolean> {
		Dao<Song, Integer> dao;
		ImageButton button;

		TaskFavorites(Dao<Song, Integer> dao, ImageButton pButton) {
			this.dao = dao;
			button = pButton;
		}

		@Override
		protected Boolean doInBackground(Integer... params) {
			try {
				return dao.idExists(params[0]);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result) {
				button.setImageResource(R.drawable.star_on);
			} else {
				button.setImageResource(R.drawable.star_off);
			}
			super.onPostExecute(result);
		}

	}

}