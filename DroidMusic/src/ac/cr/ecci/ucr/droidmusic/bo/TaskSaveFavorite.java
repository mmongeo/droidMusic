package ac.cr.ecci.ucr.droidmusic.bo;

import java.lang.ref.WeakReference;
import java.sql.SQLException;

import ac.cr.ecci.ucr.droidmusic.R;
import ac.cr.ecci.ucr.droidmusic.data.DBHelper;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageButton;

import com.j256.ormlite.dao.Dao;

public class TaskSaveFavorite extends AsyncTask<Song, Void, Void> {
	ProgressDialog progress;
	WeakReference ctx;
	ImageButton pressed;
	boolean esFavorito;
	Context context;
	MusicAdapter adapter = null;

	public void setButtonPressed(ImageButton button) {
		pressed = button;
	}

	public void setContext(Context context) {
		context = context;
	}

	public TaskSaveFavorite() {
		super();
		// attach(activity);
	}

	public TaskSaveFavorite(MusicAdapter self) {
		adapter = self;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pressed.setEnabled(false);
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		changeButton();
		pressed.setEnabled(true);
	}

	public void attach(Activity activity) {
		this.ctx = new WeakReference(activity);
	}

	public void deattach() {
		this.ctx = null;
	}

	@Override
	protected Void doInBackground(Song... params) {
		// if (ctx != null && ctx.get() != null) {
		DBHelper helper = DBHelper.getHelper(context);
		Dao<Song, Integer> dao;
		try {
			dao = helper.getDao();
			if (dao.idExists(params[0].getTrackId())) {
				dao.delete(params[0]);
				esFavorito = false;
			} else {
				dao.create(params[0]);
				esFavorito = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// }
		}
		return null;
	}

	public void changeButton() {
		if (esFavorito) {
			pressed.setImageResource(R.drawable.star_on);
		} else {
			if (adapter != null) { // si ademas de quitar un favorito tiene que
									// actualizar el adapter
				int pos = (Integer) pressed.getTag(R.id.id_tag_boton);
				adapter.removeSong(pos);
			} else {
				pressed.setImageResource(R.drawable.star_off); //si lo va a borrar..

			}
		}
	}

};