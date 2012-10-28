package ac.cr.ecci.ucr.droidmusic.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import ac.cr.ecci.ucr.droidmusic.bo.ItunesSongRequest;
import ac.cr.ecci.ucr.droidmusic.bo.Song;
import ac.cr.ecci.ucr.droidmusic.service.HTTPClientFactory.ClientType;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.JsonReader;
import android.util.Log;
import android.app.Service;

public class ItunesMusicService extends DroidMusicService {

	public static final int OK = 1;
	public static final int NADA = 2;
	public static final int ERROR = 0;

//	private static final String ARTIST = "artistName";
//	private static final String TRACKNAME = "trackName";
//	private static final String TRACKID = "trackId";
//	private static final String TRACKART = "artworkUrl60";
//	private static final String RESULTSCOUNT = "resultCount";
//	private static final String RESULTS = "results";

	private int estado = -1;
//	private JSONArray query = null;
	int counter = 0;
	int max;
	private Gson gson;
	private ItunesSongRequest data;

	public ItunesMusicService() {
		gson = new Gson();
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	private final String itunesURL = "https://itunes.apple.com/search?";

	public boolean newSearch(String criteria) {
		counter = 0;
		if (criteria != "") { // si criteria no lleva nada ni siquiera busca
			try {
				String searchValue = "limit=500" + "&term="
						+ URLEncoder.encode(criteria, "UTF-8") + "&media=music";

				InputStream in = HTTPClientFactory.getInputStream(itunesURL
						+ searchValue, ClientType.HTTP_URL_CONNECTION);
				data = gson.fromJson(new BufferedReader(new InputStreamReader(
						in, "UTF-8")), ItunesSongRequest.class);
				max = data.getResultCount();
				return true;
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			estado = NADA;
			data = null;
//			query = null;
		}
		return false;

	}

	/* Antes de esto es bueno revisar si esta conectado a internet */
	@Override
	public List<Song> getSongs(int quantity) {
		List<Song> songs = null;
		int localCounter = 0;
	
		if (data != null && counter < max) {
				songs = new ArrayList<Song>();
				while (localCounter < quantity && counter < max) {
					songs.add(data.getSong(counter));
//					temp = query.getJSONObject(counter);
//					Song aux = new Song(temp
//							.getString(ItunesMusicService.TRACKNAME), temp
//							.getString(ItunesMusicService.ARTIST), temp
//							.getString(ItunesMusicService.TRACKART), temp
//							.getString(ItunesMusicService.TRACKID));
//					songs.add(aux);
					Log.d("num", "" + localCounter );
					++counter;
					++localCounter;
				}
		}
		return songs;
	}
}
