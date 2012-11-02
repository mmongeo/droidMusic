package ac.cr.ecci.ucr.droidmusic.bo;

import java.util.List;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;

public class RetainedInstances {
	private AsyncTask task;
	private List<Song> songs;
	private MusicAdapter adapter;
	private int estadoFooter = 0;
	AlertDialog dialog;
	public AlertDialog getDialog() {
		return dialog;
	}

	public MusicAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(MusicAdapter adapter) {
		this.adapter = adapter;
	}

	public void setDialog(AlertDialog dialog) {
		this.dialog = dialog;
	}

	public static final int FOOTERINVIS = 0;
	public static final int FOOTERVIS = 1;
	public static final int FOOTERNOHAY = 2;
	public static final int FOOTERVERMAS = 3;

	public RetainedInstances() {

	}

	public AsyncTask getTask() {
		return task;
	}

	public void setTask(AsyncTask task) {
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

	public static void revisarFooter(int modo, View footer, Button verMas) {
		switch (modo) {
		case RetainedInstances.FOOTERINVIS:
			footer.setVisibility(View.INVISIBLE);
			break;
		case RetainedInstances.FOOTERVIS:
			footer.setVisibility(View.INVISIBLE);
			break;
		case RetainedInstances.FOOTERVERMAS:
			footer.setVisibility(View.VISIBLE);
			verMas.setEnabled(true);
			verMas.setText("Ver mas resultados");
			break;
		case RetainedInstances.FOOTERNOHAY:
			footer.setVisibility(View.VISIBLE);
			verMas.setEnabled(false);
			verMas.setText("No hay mas resultados");
			break;

		}

	}

};
